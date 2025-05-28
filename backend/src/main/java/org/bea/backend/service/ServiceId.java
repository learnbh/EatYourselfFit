package org.bea.backend.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ServiceId {

    public String generateId(){
        return UUID.randomUUID().toString();
    }
}
