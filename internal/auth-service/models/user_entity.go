package models

type User struct {
	Id        string `gorm:"primary_key;column:id"`
	Name      string `gorm:"column:name"`
	Email     string `gorm:"column:email"`
	Password  string `gorm:"column:password"`
	CreatedAt int64  `gorm:"column:created_at;autoCreateTime:mill"`
}

func (u User) TableName() string {
	return "users"
}
