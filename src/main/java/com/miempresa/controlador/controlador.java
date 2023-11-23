package com.miempresa.controlador;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miempresa.interfaceServicio.IEmpleadoServicio;
import com.miempresa.interfaceServicio.ITareaServicio;
import com.miempresa.interfaces.ITarea;
import com.miempresa.modelo.Empleado;
import com.miempresa.modelo.Tarea;

@Controller
@RequestMapping
public class controlador {
	
	@Autowired
	private ITareaServicio tarea;
	
	@GetMapping("/listarTareas")
	public String listarTareas(Model model) {
		List<Tarea>tareas = tarea.listar();
		model.addAttribute("tareas", tareas);
		return "tareas";
	}
	
	@GetMapping("/agregarTarea")
	public String agregartarea(Model model) {
		model.addAttribute("tarea", new Tarea());
		return "agregarTarea";
	}
	
	@PostMapping("/guardarTarea")
	public String guardarTarea(Tarea p) {
		tarea.guardar(p);
		return "redirect:/listarTareas";
	}
	
	@GetMapping("/editarTarea/{id}")
	public String editarTarea(@PathVariable int id, RedirectAttributes atributos) {
		Optional<Tarea> tareaOptional = tarea.listarId(id);
	    
	    if (tareaOptional.isPresent()) {
	        Tarea tarea = tareaOptional.get();
	        atributos.addFlashAttribute("tarea", tarea);
	    } else {
	    }
	    return "redirect:/mostrarTarea";	
	}
	
	@GetMapping("/mostrarTarea")
	public String mostrarTarea(@ModelAttribute("tarea") Tarea p, Model model) {
		model.addAttribute("tarea", p);
		return "agregarTarea";
	}
	
	@GetMapping("/eliminarTarea/{id}")
	public String eliminarTarea(@PathVariable int id) {
		tarea.borrar(id);
		return "redirect:/listarTareas";
	}
	
	@Autowired
	private IEmpleadoServicio servicio;
	
	@GetMapping("/listarEmpleados")
	public String listarEmpleados(Model model) {
		List<Empleado>empleados = servicio.listar();
		model.addAttribute("empleados", empleados);
		return "empleados";
	}
	
	@GetMapping("/agregarEmpleado")
	public String agregarEmpleado(Model model) {
		model.addAttribute("empleado", new Empleado());
		return "agregarEmpleado";
	}
	
	@PostMapping("/guardarEmpleado")
	public String guardarEmpleado(Empleado p) {
		servicio.guardar(p);
		return "redirect:/listarEmpleados";
	}
	
	@GetMapping("/editarEmpleado/{id}")
	public String editarEmpleado(@PathVariable int id, RedirectAttributes atributos) {
		Optional<Empleado> empleado = servicio.listarId(id);
		atributos.addFlashAttribute("empleado", empleado);
		return "redirect:/mostrarEmpleado";
	}
	
	@GetMapping("/mostrarEmpleado")
	public String mostrarEmpleado(@ModelAttribute("empleado") Empleado p, Model model) {
		model.addAttribute("empleado", p);
		return "agregarEmpleado";
	}
	
	@GetMapping("/eliminarEmpleado/{id}")
	public String eliminarEmpleado(@PathVariable int id) {
		servicio.borrar(id);
		return "redirect:/listarEmpleados";
	}
	
	@PostMapping("/buscar")
    public String buscar(@RequestParam String nombre, @RequestParam String tipoTabla, Model model) {
        if ("tareas".equals(tipoTabla)) {
            List<Tarea> tareasEncontradas = tarea.buscarPorDescripcionContaining(nombre);
            model.addAttribute("tareas", tareasEncontradas);
            return "buscarTarea";
        } else if ("empleados".equals(tipoTabla)) {
            List<Empleado> empleadosEncontrados = servicio.buscarPorNombreContaining(nombre);
            model.addAttribute("empleados", empleadosEncontrados);
            return "buscarEmpleado";
        }
        return "paginaError";
    }
}
