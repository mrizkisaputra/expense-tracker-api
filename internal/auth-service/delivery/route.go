package delivery

import "github.com/gofiber/fiber/v2"

type AuthRoute struct {
	UserController UserController
	App            *fiber.App
}

func (r *AuthRoute) SetupRoutes() {
	r.App.Post("/api/v1/register", r.UserController.Register)
	r.App.Post("/api/v1/login", r.UserController.Login)
}
