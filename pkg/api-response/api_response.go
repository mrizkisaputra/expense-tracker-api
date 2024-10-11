package api_response

type ApiResponse struct {
	Status    int         `json:"status"`
	Timestamp string      `json:"timestamp"`
	Message   string      `json:"message"`
	Data      interface{} `json:"data"`
}

type ApiResponseError struct {
	Status    int         `json:"status"`
	Timestamp string      `json:"timestamp"`
	Message   string      `json:"message"`
	Errors    interface{} `json:"errors,omitempty"`
}

type ApiValidationError struct {
	Field         string `json:"field"`
	RejectedValue any    `json:"rejected_value"`
	Message       string `json:"message"`
}
