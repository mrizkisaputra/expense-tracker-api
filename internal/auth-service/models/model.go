package models

type RegisterRequest struct {
	Name     string `json:"name" validate:"required,max=100"`
	Email    string `json:"email" validate:"required,email,max=100"`
	Password string `json:"password" validate:"required,max=100"`
}

type RegisterResponse struct {
	Id        string `json:"id"`
	Name      string `json:"name"`
	Email     string `json:"email"`
	Password  string `json:"password"`
	CreatedAt int64  `json:"created_at"`
}

type LoginRequest struct {
	Email    string `json:"email" validate:"required,email,max=100"`
	Password string `json:"password" validate:"required,max=100"`
}

type TokenUserLoginResponse struct {
	JwtToken string `json:"jwt_token"`
}
