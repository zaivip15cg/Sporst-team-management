import { Form, Input, Button, DatePicker, message, Card } from "antd"
import { useEffect, useState } from "react"
import axiosClient from "../../api/axiosClient"
import dayjs from "dayjs"

export default function Profile() {
      const navigate = useNavigate()
      const [loading, setLoading] = useState(false)
      const [form] = Form.useForm()
    　const [profile, setProfile] = useState(null)   

      const fetchProfile = async () => {
        setLoading(true)
        try {
            const response = await axiosClient.get("/users/myInfo")
            const data = response.data.result
            setProfile(data)
            form.setFieldsValue({
                name: data.name,
                email: data.email,
                phone: data.phone,
                role: data.role,
                dateOfBirth: data.dateOfBirth ? dayjs(data.dateOfBirth) : null
            })
        } catch (error) {
            console.error("Failed to fetch profile:", error)
            message.error("Failed to load profile")
        } finally {
            setLoading(false)
        }
      }

      useEffect(() => {fetchProfile()}, [])


      const handleSubmit = async (values) => {
        setLoading(true)
        try {   
        await axiosClient.put(`/users/${profile.id}`, {
            name: values.name,
            phone: values.phone,
            dateOfBirth: values.dateOfBirth?.format("YYYY-MM-DD"),
        })
        message.success("Cập nhật thành công")
    } 
        catch (error) {
            console.error("Failed to update profile:", error)
            message.error("Failed to update profile")
        } finally {
            setLoading(false)
        }
    }
    


    return <Card title="My Profile" style={{ maxWidth: 600, margin: "0 auto" }}>
        <Form form={form} layout="vertical" onFinish={handleSubmit}>
        
            <Form.Item label="Name" name="name">
                <Input />
            </Form.Item>
            <Form.Item label="Email" name="email">
                <Input disabled />
            </Form.Item>
            <Form.Item label="Phone" name="phone">
                <Input />
            </Form.Item>
            <Form.Item label="Date of Birth" name="dateOfBirth">
                <DatePicker style={{ width: "100%" }} />    
            </Form.Item>
            <Form.Item label="Role" name="role">
                <Input disabled />
            </Form.Item>
            <Form.Item>
                <Button type="primary" htmlType="submit">
                    Update Profile
                </Button>
            </Form.Item>

        </Form>
    </Card>
        

    
}
