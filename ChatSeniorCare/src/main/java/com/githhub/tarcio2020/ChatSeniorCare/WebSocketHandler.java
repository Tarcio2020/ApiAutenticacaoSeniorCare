package com.githhub.tarcio2020.ChatSeniorCare;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Extraímos o 'userId' da URI da sessão
        String userId = extractUserIdFromSession(session);
        
        // Armazenamos o 'userId' nos atributos da sessão para usá-lo ao fechar a conexão
        session.getAttributes().put("userId", userId);
        sessions.put(userId, session);
        System.out.println("Usuário conectado: " + userId);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Mensagem recebida: " + message.getPayload());
        
        // Desserializando a mensagem JSON para o objeto MensagensChat
        MensagensChat mensagensChat = objectMapper.readValue(message.getPayload(), MensagensChat.class);
        
        // Verificando se a sessão do destinatário existe
        WebSocketSession receiverSession = sessions.get(mensagensChat.getDestinatarioId());
        if (receiverSession != null && receiverSession.isOpen()) {
            receiverSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(mensagensChat)));
        } else {
            System.out.println("Destinatário não encontrado ou desconectado.");
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = (String) session.getAttributes().get("userId");

        if (userId != null) {
            sessions.remove(userId);
            System.out.println("Usuário desconectado: " + userId);
        } else {
            System.out.println("Erro: 'userId' não encontrado ao fechar a conexão.");
        }
    }

    // Método auxiliar para extrair o 'userId' da URI da sessão
    private String extractUserIdFromSession(WebSocketSession session) {
        // Aqui você pode melhorar a extração do 'userId' da URI
        String userId = session.getUri().getQuery().split("=")[1]; 
        return userId;
    }
}
