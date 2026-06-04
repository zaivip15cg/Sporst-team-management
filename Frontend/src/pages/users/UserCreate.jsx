import { Form, Input, Button, Select, Card, message } from "antd"
import { useNavigate } from "react-router-dom"
import axiosClient from "../../api/axiosClient"
import { useState } from "react"


export default function UserCreate() {
    const navigate = useNavigate()
    const [form] = Form.useForm()
    const [loading, setLoading] = useState(false)

    const onFinish = async (values) => {
        setLoading(true)

     
            try {
                await axiosClient.post("/users", values) 
                message.success("User created successfully")
                navigate("/users")
            } catch (error) {
                console.error("Failed to create user:", error)
                message.error("Failed to create user")
            }      
            finally {
                setLoading(false)
            }
        }
    


    return (
        <Card title="Create User" style={{ maxWidth: 600, margin: "0 auto" }}> 
            <Form form={form} layout="vertical" onFinish={onFinish}>
                <Form.Item label="Name" name="name" rules={[{ required: true, message: "Please enter the user's name" }]}>
                    <Input placeholder="Enter name" />
                </Form.Item>
                <Form.Item label="Email" name="email" rules={[{ required: true, message: "Please enter the user's email" },{type: "email", message: "Invalid email format" }]}>
                    <Input placeholder="Enter email" />
                </Form.Item>
                <Form.Item label="Phonenumber" name="phone" rules={[{ required: true, message: "Please enter the user's phonenumber" }]}>
                    <Input placeholder="Enter phonenumber" />
                </Form.Item>
                <Form.Item label="Password" name="password" rules={[{ required: true, message: "Please enter the user's password" },{ min: 6, message: "Password must be at least 6 characters" }]}>
                    <Input.Password placeholder="Enter password" />
                </Form.Item>
                <Form.Item label="Role" name="role" rules={[{ required: true, message: "Please select the user's role" }]}>
                    <Select placeholder="Select role">
                        <Select.Option value="COACH">COACH</Select.Option>
                        <Select.Option value="USER">User</Select.Option>
                    </Select>
                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit" loading={loading}>
                        Create User
                    </Button>
                </Form.Item>

                </Form>

                
                
                
                
                </Card>)
                }
    