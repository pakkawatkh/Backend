package com.example.backend.model.userModel;

import com.example.backend.entity.User;
import lombok.Data;

@Data
public class LoginResponse {
  private String firstname;
  private String lastname;
  private User.Role role;
  private String picture;
}
