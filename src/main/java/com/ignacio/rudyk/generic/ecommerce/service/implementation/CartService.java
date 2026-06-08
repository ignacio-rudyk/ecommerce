package com.ignacio.rudyk.generic.ecommerce.service.implementation;

import com.ignacio.rudyk.generic.ecommerce.dto.CartDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.CartProductDTO;
import com.ignacio.rudyk.generic.ecommerce.exception.BadRequestException;
import com.ignacio.rudyk.generic.ecommerce.exception.DataNotFoundException;
import com.ignacio.rudyk.generic.ecommerce.mapper.ICartMapper;
import com.ignacio.rudyk.generic.ecommerce.mapper.ICartProductMapper;
import com.ignacio.rudyk.generic.ecommerce.repository.ICartProductRepository;
import com.ignacio.rudyk.generic.ecommerce.repository.ICartRepository;
import com.ignacio.rudyk.generic.ecommerce.repository.IProductRepository;
import com.ignacio.rudyk.generic.ecommerce.repository.entity.Cart;
import com.ignacio.rudyk.generic.ecommerce.repository.entity.CartProduct;
import com.ignacio.rudyk.generic.ecommerce.repository.entity.Product;
import com.ignacio.rudyk.generic.ecommerce.service.ICartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {

    private ICartRepository cartRepository;

    private IProductRepository productRepository;

    private ICartProductRepository cartProductRepository;

    private ICartMapper cartMapper;

    private ICartProductMapper cartProductMapper;

    public CartService(ICartRepository cartRepository,
                       IProductRepository productRepository,
                       ICartProductRepository cartProductRepository,
                       ICartMapper cartMapper,
                       ICartProductMapper cartProductMapper) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartProductRepository = cartProductRepository;
        this.cartMapper = cartMapper;
        this.cartProductMapper = cartProductMapper;
    }

    @Override
    @Transactional
    public void createCart(Long userId) {
        Cart cart = new Cart(userId);
        cartRepository.save(cart);
    }

    @Override
    public void deleteCart(Long userId) {
        if(userId != null) {
            Optional<Cart> opCart = cartRepository.findByUserId(userId);
            if(opCart.isPresent()) {
                cartRepository.delete(opCart.get());
            } else {
                throw new DataNotFoundException("Carro no encontrado");
            }
        } else {
            throw new BadRequestException("El ID es null");
        }
    }

    @Override
    public void emptyCart(Long cartId) {
        if(cartId == null) {
            throw new BadRequestException("El ID del carro es null");
        } else {
            Optional<Cart> opCart = cartRepository.findById(cartId);
            if (opCart.isPresent()) {
                cartProductRepository.deleteByCartId(cartId);
                Cart cart = opCart.get();
                cart.setSubTotalAmount(BigDecimal.ZERO);
                cart.setTotalAmount(BigDecimal.ZERO);
                cart.setLastModification(new Date());
                cartRepository.save(cart);
            } else {
                throw new DataNotFoundException("Carro no encontrado");
            }
        }

    }

    @Override
    @Transactional(readOnly = true)
    public CartDTO getCart(Long id) {
        CartDTO cartDTO = cartMapper.toDTO(getEmptyCartById(id));
        if(cartDTO != null)
            return cartDTO.withProducts(findProductsFromCart(id));
        throw new DataNotFoundException("Carro no encontrado");
    }

    @Override
    @Transactional
    public void addProduct(Long cartId, Long productId) {
        CartProduct cartProduct = getCartProductByCartIdAndProductId(cartId, productId);
        if(cartProduct == null) {
            Optional<Product> opProduct = productRepository.findById(productId);
            if(opProduct.isPresent())
                cartProduct = new CartProduct(cartId, opProduct.get(), 1L);
            else
                throw new DataNotFoundException("Producto no encontrado");
        } else {
            cartProduct.setQuantity(cartProduct.getQuantity() + 1L);
        }
        cartProductRepository.save(cartProduct);
        updateAmountCart(cartId, cartProduct.getProduct(), true);
    }
    
    @Override
    @Transactional
    public void deleteProduct(Long cartId, Long productId) {
        CartProduct cartProduct = getCartProductByCartIdAndProductId(cartId, productId);
        if(cartProduct == null) {
            throw new DataNotFoundException("El producto no se encuentra en el carrito");
        } else if(cartProduct.getQuantity() <= 1L) {
            cartProductRepository.delete(cartProduct);
        } else {
            cartProduct.setQuantity(cartProduct.getQuantity() - 1L);
            cartProductRepository.save(cartProduct);
        }
        updateAmountCart(cartId, cartProduct.getProduct(), false);
    }
    
    private void updateAmountCart(Long cartId, Product product, boolean isAdd) {
        Optional<Cart> opCart = cartRepository.findById(cartId);
        if (opCart.isPresent()) {
            Cart cart = opCart.get();
            if(isAdd) {
                cart.setTotalAmount(cart.getTotalAmount().add(product.getPrice().getPrice()));
                cart.setSubTotalAmount(cart.getSubTotalAmount().add(product.getPrice().getPrice()));
            } else {
                if(cart.getTotalAmount().compareTo(product.getPrice().getPrice()) >= 0) {
                    cart.setTotalAmount(cart.getTotalAmount().subtract(product.getPrice().getPrice()));
                    cart.setSubTotalAmount(cart.getSubTotalAmount().subtract(product.getPrice().getPrice()));
                }
            }
            cart.setLastModification(new Date());
            cartRepository.save(cart);
        } else {
            throw new DataNotFoundException("Carro no encontrado");
        }
    }

    private Cart getEmptyCartById(Long id) {
        if(id == null)
            throw new BadRequestException("El ID es null");
        Optional<Cart> opCart = cartRepository.findById(id);
        return opCart.orElse(null);
    }

    private List<CartProductDTO> findProductsFromCart(Long cartId) {
        return cartProductRepository.findCartProductByCartId(cartId).stream()
                .map(p -> cartProductMapper.toDTO(p))
                .toList();
    }

    private CartProduct getCartProductByCartIdAndProductId(Long cartId, Long productId) {
        if(cartId != null && productId != null) {
            Optional<CartProduct> opCartProduct = cartProductRepository.findByCartIdAndProductId(cartId, productId);
            return opCartProduct.orElse(null);
        } else {
            throw new BadRequestException("Los IDs son nulos");
        }
    }

}