import { Form, Input, Button, Card, message } from "antd"
import { useNavigate } from "react-router-dom"
import { useAuth } from "../context/AuthContext"
import axiosClient from "../api/axiosClient"
import { useState } from "react"
import AuthLayout from "../layouts/AuthLayout"

export default function Login() {
    const { login } = useAuth()
    const navigate = useNavigate()
    const [loading, setLoading] = useState(false)

    const onFinish = async (values) => {
        setLoading(true)
        try {
            const res = await axiosClient.post("/auth/token", values)
            login(res.data.result.token)
            navigate("/dashboard")
        } catch (err) {
            message.error("Email hoặc mật khẩu không đúng")
        } finally {
            setLoading(false)
        }
    }

    return (
        <AuthLayout>
            <Card style={{ width: 400, borderRadius: 12, boxShadow: "0 4px 24px rgba(0,0,0,0.10)" }}>
                <h2 style={{ textAlign: "center", marginBottom: 24 }}>Đăng nhập</h2>
                <Form onFinish={onFinish} layout="vertical">
                    <Form.Item
                        label="Email"
                        name="email"
                        rules={[{ required: true, message: "Vui lòng nhập email" }, { type: "email", message: "Email không hợp lệ" }]}
                    >
                        <Input />
                    </Form.Item>

                    <Form.Item
                        label="Mật khẩu"
                        name="password"
                        rules={[
                            { required: true, message: "Vui lòng nhập mật khẩu" }
                        ]}
                    >
                        <Input.Password />
                    </Form.Item>

                    <div style={{ textAlign: "right", marginBottom: 16 }}>
                        <a href="/forgot-password">Quên mật khẩu?</a>
                    </div>

                    <Form.Item>
                        <Button type="primary" htmlType="submit" block loading={loading}>
                            Đăng nhập
                        </Button>
                    </Form.Item>
                </Form>
            </Card>
        </AuthLayout>
    )
}
