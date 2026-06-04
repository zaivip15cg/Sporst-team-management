import { Form, Input, Button, Select, Card, message } from "antd"
import { useNavigate, useParams } from "react-router-dom"
import { useState, useEffect } from "react"
import axiosClient from "../../api/axiosClient"





export default function UserEdit() {
    const navigate = useNavigate()
    const { id } = useParams()
    const [form] = Form.useForm()
    const [loading, setLoading] = useState(false)
    const fetchUser = async () => {
    try {
        const response = await axiosClient.get(`/users/${id}`)
        form.setFieldsValue(response.data.result)
    } catch (error) {
        message.error("Failed to fetch user")
    }
}
    useEffect(() => {fetchUser()}, [id])

    const onFinish = async (values) => {
        setLoading(true)

     
            try {
                await axiosClient.put(`/users/${id}`, values) 
                message.success("User changed successfully")
                navigate("/users")
            } catch (error) {
                console.error("Failed to change user:", error)
                message.error("Failed to change user")
            }      
            finally {
                setLoading(false)
            }
        }
    
    return  (
     <Card title="Edit User" style={{ maxWidth: 600, margin: "0 auto" }}> 
            <Form form={form} layout="vertical" onFinish={onFinish}>

            <Form.Item label="Name" name="name" rules={[{ required: true, message:"Please enter the user's name" }]}>
                    <Input placeholder="Enter name" />

                </Form.Item>
                <Form.Item label="Email" name="email" rules={[{ required: true, message:"Please enter the user's email" },{type: "email", message: "Invalid email format" }]}>
                    <Input placeholder="Enter email" />
                </Form.Item>
                <Form.Item label="Phonenumber" name="phone" rules={[{ required: true, message:"Please enter the user's phone number" }]}>
                    <Input placeholder="Enter phonenumber" />
                </Form.Item>
                <Form.Item label="Role" name="role" rules={[{ required: true, message:"Please select the user's role" }]}>
                    <Select placeholder="Select role">
                        <Select.Option value="COACH">COACH</Select.Option>
                        <Select.Option value="USER">User</Select.Option>
                    </Select>
                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit" loading={loading}>
                        Save Changes
                    </Button>
                </Form.Item>
                </Form>
    


</Card>)  }
    
