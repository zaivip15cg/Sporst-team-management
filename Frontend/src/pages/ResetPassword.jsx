import { Form, Input, Button, Card, message } from "antd"
import { useState } from "react"
import { useNavigate, useSearchParams } from "react-router-dom"
import axiosClient from "../api/axiosClient"
import AuthLayout from "../layouts/AuthLayout"

const passwordRules = [
    { required: true, message: "Vui lòng nhập mật khẩu mới" },
    { min: 8, message: "Mật khẩu phải có ít nhất 8 ký tự" },
    {
        validator: (_, value) => {
            if (!value) return Promise.resolve()
            if (!/[A-Z]/.test(value))
                return Promise.reject("Mật khẩu phải có ít nhất 1 chữ in hoa")
            if (!/[a-z]/.test(value))
                return Promise.reject("Mật khẩu phải có ít nhất 1 chữ thường")
            if (!/[0-9]/.test(value))
                return Promise.reject("Mật khẩu phải có ít nhất 1 chữ số")
            if (!/[!@#$%^&*(),.?\":{}|<>]/.test(value))
                return Promise.reject("Mật khẩu phải có ít nhất 1 ký tự đặc biệt (!@#$%...)")
            return Promise.resolve()
        }
    }
]

export default function ResetPassword() {
    const [loading, setLoading] = useState(false)
    const [form] = Form.useForm()
    const navigate = useNavigate()
    const [searchParams] = useSearchParams()
    const token = searchParams.get("token")

    const onFinish = async (values) => {
        setLoading(true)
        try {
            await axiosClient.post(`/users/reset-password`,{token, newPassword: values.newPassword, confirmPassword: values.confirmPassword})
            message.success("Đặt lại mật khẩu thành công, hãy đăng nhập lại")
            navigate("/login")
        } catch (err) {
            message.error("Đặt lại mật khẩu thất bại, token không hợp lệ hoặc đã hết hạn")
        } finally {
            setLoading(false)
        }
    }

    return (
        <AuthLayout>
            <Card style={{ width: 400, borderRadius: 12, boxShadow: "0 4px 24px rgba(0,0,0,0.10)" }}>
                <h2 style={{ textAlign: "center", marginBottom: 8 }}>Đặt lại mật khẩu</h2>
                <p style={{ textAlign: "center", color: "#888", marginBottom: 24 }}>
                    Nhập mật khẩu mới cho tài khoản của bạn, đảm bảo tuân thủ các yêu cầu về độ mạnh mật khẩu (ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt)
                </p>
                <Form form={form} onFinish={onFinish} layout="vertical">
                    <Form.Item
                        label="Mật khẩu mới"
                        name="newPassword"
                        rules={passwordRules}
                    >
                        <Input.Password placeholder="Nhập mật khẩu mới" />
                    </Form.Item>

                    <Form.Item
                        label="Xác nhận mật khẩu"
                        name="confirmPassword"
                        dependencies={["newPassword"]}
                        rules={[
                            { required: true, message: "Vui lòng xác nhận mật khẩu" },
                            ({ getFieldValue }) => ({
                                validator(_, value) {
                                    if (!value || getFieldValue("newPassword") === value)
                                        return Promise.resolve()
                                    return Promise.reject("Mật khẩu xác nhận không khớp")
                                }
                            })
                        ]}
                    >
                        <Input.Password placeholder="Nhập lại mật khẩu mới" />
                    </Form.Item>

                    <Form.Item>
                        <Button type="primary" htmlType="submit" block loading={loading}>
                            Đặt lại mật khẩu
                        </Button>
                    </Form.Item>
                </Form>
            </Card>
        </AuthLayout>
    )
}