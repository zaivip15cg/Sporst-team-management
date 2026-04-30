import { Form, Input, Button, Card, message } from "antd"
import { useState } from "react"
import axiosClient from "../api/axiosClient"
import AuthLayout from "../layouts/AuthLayout"

export default function ForgotPassword() {
    const [loading, setLoading] = useState(false)

    const onFinish = async (values) => {
        setLoading(true)
        try {
            await axiosClient.post(`/users/forgot-password?email=${values.email}`)
            message.success("Kiểm tra hộp thư để đặt lại mật khẩu")
        } catch (err) {
            message.error("Email không tồn tại trong hệ thống")
        } finally {
            setLoading(false)
        }
    }

    return (
        <AuthLayout>
            <Card style={{ width: 400, borderRadius: 12, boxShadow: "0 4px 24px rgba(0,0,0,0.10)" }}>
                <h2 style={{ textAlign: "center", marginBottom: 8 }}>Quên mật khẩu</h2>
                <p style={{ textAlign: "center", color: "#888", marginBottom: 24 }}>
                    Nhập email để nhận link đặt lại mật khẩu
                </p>
                <Form onFinish={onFinish} layout="vertical">
                    <Form.Item
                        label="Email"
                        name="email"
                        rules={[
                            { required: true, message: "Vui lòng nhập email" },
                            { type: "email", message: "Email không hợp lệ" }
                        ]}
                    >
                        <Input placeholder="example@gmail.com" />
                    </Form.Item>

                    <Form.Item>
                        <Button type="primary" htmlType="submit" block loading={loading}>
                            Gửi link đặt lại mật khẩu
                        </Button>
                    </Form.Item>
                </Form>

                <div style={{ textAlign: "center" }}>
                    <a href="/login">Quay lại đăng nhập</a>
                </div>
            </Card>
        </AuthLayout>
    )
}
