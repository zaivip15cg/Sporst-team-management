import {useState } from "react"
import {useNavigate } from "react-router-dom"
import axiosClient from "../../api/axiosClient"
import { Form, Input, Button, Card, message } from "antd"

export default function TeamCreate() {
    const navigate = useNavigate()
    const [loading, setLoading] = useState(false)   
    const onFinish = async (values) => {
        setLoading(true)
        try {
            await axiosClient.post("/teams", values) 
            message.success("Team created successfully")
            navigate("/teams")
        } catch (error) {
            console.error("Failed to create team:", error)
            message.error("Failed to create team")
        } finally {
            setLoading(false)
        }
    }
    return  (
     <Card title="Create Team" style={{ maxWidth: 600, margin: "0 auto" }}> 
            <Form layout="vertical" onFinish={onFinish}>

            <Form.Item label="Team Name" name="teamName" rules={[{ required: true, message:"Please enter the team's name" }]}>
                    <Input placeholder="Enter team name" />

                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit" loading={loading}>
                        Create Team
                    </Button>
                </Form.Item>

                </Form>

            </Card>
    )   
}
