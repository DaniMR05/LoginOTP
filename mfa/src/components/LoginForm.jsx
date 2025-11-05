// src/components/LoginForm.jsx
import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import api from "../api/api";

export default function LoginForm() {
  const [form, setForm] = useState({ email: "", password: "" });
  const navigate = useNavigate();

  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await api.post("/auth/signin", form);
      alert("📩 OTP enviado a tu correo electrónico");
      localStorage.setItem("email", form.email); // <- SOLO aquí
      navigate("/otp");
    } catch (err) {
      console.error(err);
      alert(
        err?.response?.data || "❌ Error en inicio de sesión o credenciales inválidas"
      );
    }
  };

  return (
    <div className="form-container">
      <h2>Iniciar Sesión</h2>
      <form onSubmit={handleSubmit} className="form">
        <input name="email" type="email" placeholder="Correo electrónico" value={form.email} onChange={handleChange} required />
        <input name="password" type="password" placeholder="Contraseña" value={form.password} onChange={handleChange} required />
        <button type="submit">Ingresar</button>
      </form>

      <p className="small-text">
        ¿No tienes una cuenta?{" "}
        <Link to="/register" className="link">Regístrate aquí</Link>
      </p>
    </div>
  );
}