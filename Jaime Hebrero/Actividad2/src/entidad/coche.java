package entidad;

public class coche {
	private String id;
	private String matricula;
	private String marca;
	private String modelo;
	private String color;
	public String getId() {
		return id;
	}
	public void setId(int id) {
		this.id =" 00";
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		return "Coche [Id=" + id + ", Matricula=" + matricula + ", Marca=" + marca +", Modelo=" + modelo + ", Color=" + color +"]";
	}
}
