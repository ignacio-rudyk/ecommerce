package com.ignacio.rudyk.generic.ecommerce.service.implementation;

import com.ignacio.rudyk.generic.ecommerce.dto.ProductDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.ProductRequestDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.response.PaginatedListDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.response.PaginationDTO;
import com.ignacio.rudyk.generic.ecommerce.exception.BadRequestException;
import com.ignacio.rudyk.generic.ecommerce.exception.DataNotFoundException;
import com.ignacio.rudyk.generic.ecommerce.mapper.IProductMapper;
import com.ignacio.rudyk.generic.ecommerce.repository.IFileRepository;
import com.ignacio.rudyk.generic.ecommerce.repository.IProductRepository;
import com.ignacio.rudyk.generic.ecommerce.repository.entity.*;
import com.ignacio.rudyk.generic.ecommerce.repository.ICategoryRepository;
import com.ignacio.rudyk.generic.ecommerce.service.IProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import jakarta.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    private IProductRepository productRepository;

    private ICategoryRepository categoryRepository;

    private IFileRepository fileRepository;

    private IProductMapper productMapper;

    public ProductService(IProductRepository productRepository,
                          ICategoryRepository categoryRepository,
                          IFileRepository fileRepository,
                          IProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.fileRepository = fileRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    @Override
    public void createProduct(ProductRequestDTO newProduct) {
        Product product = new Product();
        product.setTitle(newProduct.title());
        product.setDescription(newProduct.description());
        product.setShortDescription(newProduct.shortDescription());
        product.setPrice(new Price(newProduct.price(), newProduct.costPrice()));
        product.setCategory(getCategory(newProduct.categoryId()));
        product.setFileId(createFile(newProduct.base64Image(), newProduct.urlImage()));
        productRepository.save(product);
    }

    @Override
    public PaginatedListDTO<ProductDTO> findAll(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        List<ProductDTO> products = productPage.getContent().stream()
                .map(productMapper::toDTO)
                .toList();
        PaginationDTO pagination = new PaginationDTO(
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages());
        return new PaginatedListDTO<>(products, pagination);
    }

    @Override
    public ProductDTO findById(Long productId) {
        if(productId == null)
            return null;
        Optional<Product> opCart = productRepository.findById(productId);
        if(opCart.isPresent())
            return productMapper.toDTO(opCart.get());
        throw new DataNotFoundException("Producto no encontrado");
    }

    @Override
    public void updateProduct(ProductRequestDTO updateProduct) {
        if(updateProduct.id() == null)
            throw new BadRequestException("El ID es null");
        Optional<Product> opProduct = productRepository.findById(updateProduct.id());
        if(opProduct.isEmpty())
            throw new DataNotFoundException("Producto no encontrado");
        Product product = opProduct.get();
        product.setTitle(updateProduct.title());
        product.setDescription(updateProduct.description());
        product.setShortDescription(updateProduct.shortDescription());
        product.getPrice().setPrice(updateProduct.price());
        product.getPrice().setCostPrice(updateProduct.costPrice());
        product.setCategory(getCategory(updateProduct.categoryId()));
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        if(productId == null)
            throw new BadRequestException("El ID es null");
        Optional<Product> opProduct = productRepository.findById(productId);
        if(opProduct.isEmpty())
            throw new DataNotFoundException("Producto no encontrado");
        fileRepository.deleteById(opProduct.get().getFileId());
        productRepository.delete(opProduct.get());
    }

    private Category getCategory(Long categoryId) {
        if(categoryId == null)
            return null;
        Optional<Category> opCategory = categoryRepository.findById(categoryId);
        if(opCategory.isEmpty()) {
            throw new DataNotFoundException("Categoria no encontrada");
        }
        return opCategory.get();
    }

    private Long createFile(String base64, String urlImage) {
        Blob blob = null;
        if(base64 != null) {
            byte[] bytes = base64.getBytes(StandardCharsets.UTF_8);
            try {
                blob = new SerialBlob(bytes);
            } catch (SQLException e) {
                throw new BadRequestException("El dato archivo no corresponde a un formato valido");
            }
        }
        File file = new File(blob, urlImage);
        return fileRepository.save(file).getId();
    }

}
