/*
Marina Fernández Serrano: 100%
*/

package com.leftjoiners.bancosol.proyectobackend.controller;

import com.leftjoiners.bancosol.proyectobackend.dao.ColaboradoresRespository;
import com.leftjoiners.bancosol.proyectobackend.dto.Colaborador;
import com.leftjoiners.bancosol.proyectobackend.dto.ContactoColaborador;
import com.leftjoiners.bancosol.proyectobackend.dto.Localidad;
import com.leftjoiners.bancosol.proyectobackend.dto.Usuario;
import com.leftjoiners.bancosol.proyectobackend.entity.ColaboradorEntity;
import com.leftjoiners.bancosol.proyectobackend.service.ColaboradorService;
import com.leftjoiners.bancosol.proyectobackend.service.ContactoColaboradorService;
import com.leftjoiners.bancosol.proyectobackend.service.LocalidadService;
import com.leftjoiners.bancosol.proyectobackend.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@RequestMapping("/colaboradores")
@Controller
@AllArgsConstructor
public class ColaboradoresController {
    private final ColaboradorService colaboradorService;
    private final ContactoColaboradorService contactoColaboradorService;
    private final LocalidadService localidadService;
    private final UsuarioService usuarioService;


    @GetMapping("")
    public String doInit(Model model) {
        List<Colaborador> colaboradores = colaboradorService.listarColaboradores();
        List<Localidad> localidades = localidadService.listarLocalidades();
        List<Usuario> coordinadores = usuarioService.listarCoordinadores();

        model.addAttribute("colaboradores", colaboradores);
        model.addAttribute("localidades", localidades);
        model.addAttribute("coordinadores", coordinadores);
        model.addAttribute("currentSection", "colaboradores");
        return "colaboradores/colaboradores";
    }

    @GetMapping("/filtrar")
    public String doFiltrar(@RequestParam(required = false, value = "colaboraEn") Integer idLocalidad,
                            @RequestParam(required = false, value = "coordinador") Integer idCoordinador,
                            Model model) {
        List<Colaborador> colaboradores = colaboradorService.filtrarColaboradores(idLocalidad, idCoordinador);
        List<Localidad> localidades = localidadService.listarLocalidades();
        List<Usuario> coordinadores = usuarioService.listarCoordinadores();

        model.addAttribute("colaboradores", colaboradores);
        model.addAttribute("localidades", localidades);
        model.addAttribute("coordinadores", coordinadores);
        model.addAttribute("localidadSeleccionada", idLocalidad);
        model.addAttribute("coordinadorSeleccionado", idCoordinador);
        model.addAttribute("currentSection", "colaboradores");

        return "colaboradores/colaboradores";
    }


    @PostMapping("/buscarColaborador")
    public String buscarColaborador(@RequestParam("id") Integer id, Model model) {
        Colaborador colaborador = this.colaboradorService.buscarColaborador(id);
        ContactoColaborador contactoColaborador = contactoColaboradorService.buscarContactoPrincipalDe(id);

        model.addAttribute("colaborador", colaborador);
        model.addAttribute("contactoPrincipal", contactoColaborador);

        return "colaboradores/info_colaboradores";
    }

    @GetMapping("/editar")
    public String editarColaborador(@RequestParam("id") Integer id, Model model){
        Colaborador colaborador = colaboradorService.buscarColaborador(id);
        List<Localidad> localidades = localidadService.listarLocalidades();
        List<Usuario> coordinadores = usuarioService.listarCoordinadores();

        model.addAttribute("colaborador", colaborador);
        model.addAttribute("localidades", localidades);
        model.addAttribute("coordinadores", coordinadores);
        model.addAttribute("editando", true);
        model.addAttribute("currentSection", "colaboradores");

        return  "colaboradores/formulario_colaborador";
    }

    @GetMapping("/anadir")
    public String anadirColaborador(Model model) {
        Colaborador colaborador = new Colaborador();
        List<Localidad> localidades = localidadService.listarLocalidades();
        List<Usuario> coordinadores = usuarioService.listarCoordinadores();

        model.addAttribute("colaborador", colaborador);
        model.addAttribute("localidades", localidades);
        model.addAttribute("coordinadores", coordinadores);
        model.addAttribute("editando", false);
        model.addAttribute("currentSection", "colaboradores");

        return "colaboradores/formulario_colaborador";
    }

    @PostMapping("/guardar")
    public  String guardarColaborador(@RequestParam(value = "id", required = false) Integer id,
                                      @RequestParam("nombre") String nombre,
                                      @RequestParam("codigo") String codigo,
                                      @RequestParam(required = false, value = "contactoPrincipal") String contactoPrincipal,
                                      @RequestParam("domicilio") String domicilio,
                                      @RequestParam("cp") String cp,
                                      @RequestParam("localidadSede") Integer idLocalidad,
                                      @RequestParam("colaboraEn") Integer idColabora,
                                      @RequestParam("temporal") Boolean temporal,
                                      @RequestParam("coordinador") Integer idCoordinador,
                                      @RequestParam("observaciones") String observaciones,
                                      @RequestParam(required = false, value = "nuevoContactoNombre") String nombreContacto,
                                      @RequestParam(required = false, value = "nuevoContactoEmail") String emailContacto,
                                      @RequestParam(required = false, value = "nuevoContactoTelefono") String telefonoContacto){
        if(id == null && nombreContacto != null && !nombreContacto.isEmpty()) {
            contactoPrincipal = nombreContacto;
        }

        Integer idColaborador = this.colaboradorService.guardarColaborador(id,nombre,codigo,contactoPrincipal,domicilio,cp,observaciones, idLocalidad, idColabora, temporal, idCoordinador);

        if(id == null) {
            this.contactoColaboradorService.guardarContacto(id, idColaborador, nombreContacto, emailContacto, telefonoContacto);
        }


        return "redirect:/colaboradores";
    }

    @GetMapping("/eliminar")
    public String eliminarColaborador(@RequestParam("id") Integer id) {
        this.colaboradorService.eliminarColaborador(id);
        return "redirect:/colaboradores";
    }

    @GetMapping("/editarContacto")
    public String editarContacto(@RequestParam("id") Integer id, Model model) {
        ContactoColaborador contactoColaborador = this.contactoColaboradorService.buscarContacto(id);
        Colaborador colaborador = this.colaboradorService.buscarColaborador(contactoColaborador.getIdColaborador());
        List<Colaborador> colaboradores = this.colaboradorService.listarColaboradores();

        model.addAttribute("contacto", contactoColaborador);
        model.addAttribute("colaborador", colaborador);
        model.addAttribute("colaboradores", colaboradores);
        model.addAttribute("editando", true);
        model.addAttribute("currentSection", "colaboradores");

        return "colaboradores/formulario_contacto";
    }

    @PostMapping("/guardarContacto")
    public String guardarContacto(@RequestParam(required = false, value = "id") Integer id,
                                  @RequestParam("colaborador") Integer idColaborador,
                                  @RequestParam("nombre") String nombre,
                                  @RequestParam("email") String email,
                                  @RequestParam("telefono") String telefono) {
        this.contactoColaboradorService.guardarContacto(id,idColaborador,nombre,email,telefono);
        return "redirect:/colaboradores/editar?id=" + idColaborador;
    }

    @GetMapping("/anadirContacto")
    public String anadirContacto(@RequestParam("id") Integer idColaborador, Model model){
        ContactoColaborador contactoColaborador = new ContactoColaborador();
        Colaborador colaborador = this.colaboradorService.buscarColaborador(idColaborador);

        contactoColaborador.setColaborador(colaborador);

        List<Colaborador> colaboradores = this.colaboradorService.listarColaboradores();
        model.addAttribute("contacto", contactoColaborador);
        model.addAttribute("colaborador", colaborador);
        model.addAttribute("colaboradores", colaboradores);
        model.addAttribute("editando", false);
        model.addAttribute("currentSection", "colaboradores");


        return "colaboradores/formulario_contacto";
    }

    @GetMapping("/eliminarContacto")
    public String eliminarContacto(@RequestParam("id") Integer id) {
        ContactoColaborador contactoColaborador = this.contactoColaboradorService.buscarContacto(id);
        Integer idColaborador = contactoColaborador.getIdColaborador();

        this.contactoColaboradorService.eliminarContacto(id);

        return "redirect:/colaboradores/editar?id=" + idColaborador;
    }
}
