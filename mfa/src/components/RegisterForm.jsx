// src/components/RegisterForm.jsx
import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import api from "../api/api";

export default function RegisterForm() {
  const [form, setForm] = useState({
    name: "",
    lastName: "",
    email: "",
    password: "",
    confirmPassword: "",
  });
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    if (form.password !== form.confirmPassword) {
      setError("Las contraseñas no coinciden.");
      return;
    }

    try {
      await api.post("/auth/signup", {
        firstName: form.name,
        lastName: form.lastName,
        email: form.email,
        password: form.password,
      });

      alert("✅ Registro exitoso. Ahora inicia sesión.");
      // Importante: NO guardes email ni navegues a /otp
      navigate("/"); // ir al login
    } catch (err) {
      console.error(err);
      setError(
        err?.response?.data || "Error al registrar usuario. Intenta nuevamente."
      );
    }
  };

  return (
    <div className="form-container">
      <h2>Registro de Usuario</h2>

      <form onSubmit={handleSubmit} className="form">
        <input name="name" type="text" placeholder="Nombre" value={form.name} onChange={handleChange} required />
        <input name="lastName" type="text" placeholder="Apellidos" value={form.lastName} onChange={handleChange} required />
        <input name="email" type="email" placeholder="Correo electrónico" value={form.email} onChange={handleChange} required />
        <input name="password" type="password" placeholder="Contraseña" value={form.password} onChange={handleChange} required />
        <input name="confirmPassword" type="password" placeholder="Confirmar contraseña" value={form.confirmPassword} onChange={handleChange} required />

        {error && <p className="msg-error">{error}</p>}
        <button type="submit">Registrarse</button>
      </form>

      <p className="small-text">
        ¿Ya tienes una cuenta?{" "}
        <Link to="/" className="link">Inicia sesión aquí</Link>
      </p>
    </div>
  );
}