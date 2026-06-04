import { Table, Button, Space, Modal, Form, InputNumber, Select, message } from "antd"
import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import { useParams } from "react-router-dom"
import axiosClient from "../../api/axiosClient"

export default function TeamDetail() {
    const navigate = useNavigate()
    const [team, setTeam] = useState(null)
    const [members, setMembers] = useState([])
    const [positions, setPositions] = useState([])
    const [loading, setLoading] = useState(false)


    const  [editModalOpen , setEditModalOpen] = useState(false)
    const [editingMember, setEditingMember] = useState(null)
    const [editForm] = Form.useForm()

    const [addModalOpen, setAddModalOpen] = useState(false)
    const [userOptions, setUserOptions] = useState([])
    const [addForm] = Form.useForm()
    

    const { id } = useParams()

    const fetchTeam = async () => {
        setLoading(true)
        try {
            const response = await axiosClient.get(`/teams/${id}`)
            setTeam(response.data.result)
        } catch (error) {
            console.error("Failed to fetch team:", error)
            message.error("Failed to fetch team details")
        } finally {
            setLoading(false)
        }
    }

    const fetchMembers = async () => {
        setLoading(true)
        try {
            const response = await axiosClient.get(`/teams/${id}/members`)
            setMembers(response.data.result)
        } catch (error) {
            console.error("Failed to fetch members:", error)
            message.error("Failed to fetch members")
        } finally {
            setLoading(false)
        }
    }           

    const fetchPositions = async () => {
        setLoading(true)
        try {
            const response = await axiosClient.get("/positions")
            setPositions(response.data.result)
        }       catch (error) {
            console.error("Failed to fetch positions:", error)
            message.error("Failed to fetch positions")
        } finally {
            setLoading(false)
        }
    }

    useEffect(() => {
        fetchTeam()
        fetchMembers()
        fetchPositions()
    }, [id])

    const handleUserSearch = async (keyword) => {
if (!keyword) {
    setUserOptions([])
    return
}

        try {
            const response = await axiosClient.get("/users/search", { params: { keyword } })
            setUserOptions(response.data.result.map(u => ({ label: u.name, value: u.id })))
        } catch (error) {
            console.error("Failed to search users:", error)
            message.error("Failed to search users")
        }
    }



    const handleEditMember = async (record) => {

     setEditingMember(record)
        editForm.setFieldsValue({
            jerseyNumber: record.jerseyNumber,
            positionId: positions.find(p => p.name === record.positionName)?.id ?? null
        })
        setEditModalOpen(true)
    }

    const handleEditSubmit = async () => {
        try {
            const values = await editForm.validateFields()
            await axiosClient.put(`/teams/${id}/members/${editingMember.userId}`, values)
            message.success("Member updated successfully")
            setEditModalOpen(false)
            fetchMembers()
        } catch (error) {
            console.error("Failed to update member:", error)
            message.error("Failed to update member")
        }
    }

    const handleAddSubmit = async () => {
        try {
            const values = await addForm.validateFields()
            await axiosClient.post(`/teams/${id}/members`, values)
            message.success("Member added successfully")
            setAddModalOpen(false)
            fetchMembers()
        } catch (error) {
            console.error("Failed to add member:", error)
            message.error("Failed to add member")
        }
    }

    const handleDeleteMember = async (record) => {
        Modal.confirm({
            title: "Confirm Deletion",
            content: `Are you sure you want to remove ${record.userName} from the team?`,
            onOk: async () => {
                try {
                    await axiosClient.delete(`/teams/${id}/members/${record.userId}`)
                    message.success("Member removed successfully")
                    fetchMembers()
                } catch (error) {
                    console.error("Failed to remove member:", error)
                    message.error("Failed to remove member")
                }
            }
        })
    }

     const columns = [
        {
            title: "Tên thành viên",
            dataIndex: "userName",
            key: "userName",
        },
        {
            title: "Số áo",
            dataIndex: "jerseyNumber",
            key: "jerseyNumber",
        },
        {
            title: "Vị trí",
            dataIndex: "positionName",
            key: "positionName",
        },
        {
            title: "Ngày gia nhập",
            dataIndex: "createdAt",
            key: "createdAt",
        },
        {
            title: "Action",
            key: "action",
            render: (_, record) => (
                <Space size="middle">
                    <Button type="primary" onClick={() => handleEditMember(record)}>
                        Sửa
                    </Button>
                    <Button type="primary" danger onClick={() => handleDeleteMember(record)}>
                        Xóa
                    </Button>
                </Space>
            ),
        },
    ]

    return (
        <div style={{ maxWidth: 800, margin: "0 auto" }}>
            {team && (
                <div style={{ marginBottom: 16 }}>
                    <h1>{team.teamName}</h1>
                    <p>Số thành viên: {team.memberCount}</p>
                    <p>Người quản lý: {team.managerName}</p>
                    <p>Ngày tạo: {team.createdAt}</p>
                    <Button type="primary" onClick={() => setAddModalOpen(true)}>
                        Thêm thành viên
                    </Button>
                </div>
            )}

            <Table
                columns={columns}
                dataSource={members}
                rowKey="userId"
                loading={loading}
            />

            {/* Modal thêm thành viên */}
            <Modal
                title="Thêm thành viên"
                open={addModalOpen}
                onOk={handleAddSubmit}
                onCancel={() => { setAddModalOpen(false); addForm.resetFields(); setUserOptions([]) }}
                okText="Thêm"
                cancelText="Hủy"
            >
                <Form form={addForm} layout="vertical">
                    <Form.Item label="Tìm người dùng" name="userId" rules={[{ required: true, message: "Chọn người dùng" }]}>
                        <Select
                            showSearch
                            filterOption={false}
                            onSearch={handleUserSearch}
                            options={userOptions}
                            placeholder="Nhập tên để tìm kiếm..."
                            notFoundContent="Không tìm thấy"
                        />
                    </Form.Item>
                    <Form.Item label="Số áo" name="jerseyNumber">
                        <InputNumber min={1} style={{ width: "100%" }} />
                    </Form.Item>
                    <Form.Item label="Vị trí" name="positionId">
                        <Select
                            options={positions.map(p => ({ label: p.name, value: p.id }))}
                            allowClear
                            placeholder="Chọn vị trí"
                        />
                    </Form.Item>
                </Form>
            </Modal>

            {/* Modal sửa thành viên */}
            <Modal
                title="Sửa thông tin thành viên"
                open={editModalOpen}
                onOk={handleEditSubmit}
                onCancel={() => setEditModalOpen(false)}
                okText="Lưu"
                cancelText="Hủy"
            >
                <Form form={editForm} layout="vertical">
                    <Form.Item label="Số áo" name="jerseyNumber">
                        <InputNumber min={1} style={{ width: "100%" }} />
                    </Form.Item>
                    <Form.Item label="Vị trí" name="positionId">
                        <Select
                            options={positions.map(p => ({ label: p.name, value: p.id }))}
                            allowClear
                            placeholder="Chọn vị trí"
                        />
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    )

     
        
        
    

}