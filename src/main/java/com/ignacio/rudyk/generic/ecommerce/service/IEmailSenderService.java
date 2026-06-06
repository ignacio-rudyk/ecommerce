package com.ignacio.rudyk.generic.ecommerce.service;

import java.util.Map;

public interface IEmailSenderService {

    void send(String to, String templateCode);

    void send(String to, String templateCode, Map<String, String> variables);

}
