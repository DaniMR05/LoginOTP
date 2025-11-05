// src/components/OtpForm.jsx
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";

export default function OtpForm() {
  const [otp, setOtp] = useState("");
  const [email, setEmail] = useState("");
  const nav = useNavigate();

  useEffect(() => {
    const e = localStorage.getItem("email");
    if (!e) {
      alert("Primero inicia sesión para solicitar tu OTP.");
      nav("/");
      return;
    }
    setEmail(e);
  }, [nav]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await api.post("/otp/verify", {
        email: email,
        code: isNaN(Number(otp)) ? otp : Number(otp),
      });
      alert("✅ " + (res?.data || "OTP verificado"));
      localStorage.removeItem("email");
      // nav("/dashboard");
    } catch (err) {
      console.error(err);
      alert(err?.response?.data || "❌ Código inválido o expirado");
    }
  };

  // Botón "Reenviar" opcional:
  const resend = async () => {
    try {
      await api.post(`/otp/send?email=${encodeURIComponent(email)}`);
      alert("📨 OTP reenviado");
    } catch (err) {
      console.error(err);
      alert(err?.response?.data || "No se pudo reenviar el OTP");
    }
  };

  return (
    <form onSubmit={handleSubmit} className="form">
      <h2>Verificación OTP</h2>
      <input
        type="text"
        maxLength={6}
        placeholder="Ingresa el código OTP"
        value={otp}
        onChange={(e) => setOtp(e.target.value)}
        required
      />
      <button type="submit">Verificar</button>
      <p className="small-text" style={{ marginTop: 8 }}>
        ¿No llegó? <button type="button" className="link" onClick={resend}>Reenviar código</button>
      </p>
    </form>
  );
}