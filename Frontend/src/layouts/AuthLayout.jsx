import { TrophyOutlined } from "@ant-design/icons"

export default function AuthLayout({ children }) {
    return (
        <div style={{
            display: "flex",
            minHeight: "100vh",
        }}>
            {/* Nửa trái — branding */}
            <div style={{
                flex: 1,
                background: "linear-gradient(135deg, #0f2027, #203a43, #2c5364)",
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                justifyContent: "center",
                padding: "48px",
                color: "#fff",
            }}>
                <TrophyOutlined style={{ fontSize: 72, color: "#fadb14", marginBottom: 24 }} />
                <h1 style={{ color: "#fff", fontSize: 36, fontWeight: 700, margin: 0 }}>
                    SportTeam
                </h1>
                <p style={{ color: "rgba(255,255,255,0.7)", fontSize: 16, marginTop: 12, textAlign: "center" }}>
                    Hệ thống quản lý đội bóng chuyên nghiệp
                </p>

                {/* Vòng trang trí */}
                <div style={{ marginTop: 48, display: "flex", gap: 12 }}>
                    {[80, 56, 40].map((size, i) => (
                        <div key={i} style={{
                            width: size,
                            height: size,
                            borderRadius: "50%",
                            border: "2px solid rgba(255,255,255,0.2)",
                        }} />
                    ))}
                </div>
            </div>

            {/* Nửa phải — form */}
            <div style={{
                flex: 1,
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                background: "#f0f2f5",
                padding: "48px",
            }}>
                {children}
            </div>
        </div>
    )
}
