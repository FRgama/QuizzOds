package com.example.quizzods;

public class UpRequest {
    // classe do cadastro
    private String username;

    public UpRequest(String username) {
        this.username = username;
    }
    // a classe criada para a resposta do servidor

    public class SignUpResponse{
        private boolean success;
        private String message;

        public boolean isSuccess (){
            return success;
        }
        public String getMessage(){
            return message;
        }
    }
}
