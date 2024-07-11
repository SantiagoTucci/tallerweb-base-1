package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.reto.RepositorioReto;
import com.tallerwebi.dominio.reto.Reto;
import com.tallerwebi.dominio.usuario.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioReto")
public class RepositorioRetoImpl implements RepositorioReto {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioRetoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarReto(Reto reto) {
        Session session = sessionFactory.getCurrentSession();
        session.save(reto);
    }

    @Override
    public Reto obtenerRetoDisponible() {
        Session session = this.sessionFactory.getCurrentSession();
        String hql = "FROM Reto WHERE seleccionado = false ORDER BY rand()";
        Query <Reto> query = session.createQuery(hql, Reto.class).setMaxResults(1);
        return query.uniqueResult();
    }

    @Override
    public Reto obtenerRetoDisponibleParaUsuario(Usuario usuario) {
        Session session = this.sessionFactory.getCurrentSession();
        String hql = "FROM Reto r WHERE r.usuario.id = :usuarioId AND r.seleccionado = false ORDER BY rand()";
        Query<Reto> query = session.createQuery(hql, Reto.class)
                .setParameter("usuarioId", usuario.getId())
                .setMaxResults(1);
        return query.uniqueResult();
    }

    @Override
    public Reto obtenerRetoPorId(Long retoId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Reto.class, retoId); // Utiliza session.get para obtener el reto por ID
    }

    @Override
    public Reto obtenerRetoPorIdPorUsuario(Long retoId, Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Reto r WHERE r.id = :retoId AND r.usuario.id = :usuarioId";
        return session.createQuery(hql, Reto.class)
                .setParameter("retoId", retoId)
                .setParameter("usuarioId", usuario.getId())
                .uniqueResult();
    }

    @Override
    public void actualizarReto(Reto reto) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(reto);
        session.flush(); // Sincronizar los cambios con la base de datos
    }

    @Override
    public void actualizarRetoPorUsuario(Reto reto, Usuario usuario) {
        Session session = this.sessionFactory.getCurrentSession();
        String hql = "FROM Reto r WHERE r.id = :retoId AND r.usuario.id = :usuarioId";
        Reto retoExistente = session.createQuery(hql, Reto.class)
                .setParameter("retoId", reto.getId())
                .setParameter("usuarioId", usuario.getId())
                .uniqueResult();

        if (retoExistente != null) {
            retoExistente.setNombre(reto.getNombre());
            retoExistente.setEnProceso(reto.getEnProceso());
            retoExistente.setSeleccionado(reto.getSeleccionado());
            session.update(retoExistente);
            session.flush(); // Sincronizar los cambios con la base de datos
        } else {
            throw new RuntimeException("Reto no encontrado para el usuario especificado");
        }
    }

    @Override
    public Reto obtenerRetoEnProceso() {
        Session session = this.sessionFactory.getCurrentSession();
        String hql = "FROM Reto WHERE seleccionado = true AND enProceso = true";
        Query<Reto> query = session.createQuery(hql, Reto.class);
        query.setMaxResults(1);
        return query.uniqueResult();
    }

    @Override
    public Reto obtenerRetoEnProcesoDeUsuario(Usuario usuario) {
        String hql = "SELECT r FROM Usuario u JOIN u.reto r WHERE u.id = :usuarioId AND r.enProceso = true AND r.seleccionado = true";
        return this.sessionFactory.getCurrentSession()
                .createQuery(hql, Reto.class)
                .setParameter("usuarioId", usuario.getId())
                .uniqueResult();
    }

    @Override
    public List<Reto> obtenerTodosLosRetos() {
        Session session = this.sessionFactory.getCurrentSession();
        String hql = "FROM Reto";
        Query<Reto> query = session.createQuery(hql, Reto.class);
        return query.list();
    }

    @Override
    public List<Reto> obtenerTodosLosRetosDeUsuario(Usuario usuario) {
        Session session = this.sessionFactory.getCurrentSession();
        String hql = "FROM Reto r WHERE r.usuario.id = :usuarioId";
        Query<Reto> query = session.createQuery(hql, Reto.class);
        query.setParameter("usuarioId", usuario.getId());
        return query.list();
    }

    @Override
    public void guardarRetoUsuario(Reto reto, Usuario usuario) {
        Usuario usuarioBuscado = this.getUsuarioById(usuario.getId());
        usuarioBuscado.getRetos().add(reto);
        this.sessionFactory.getCurrentSession().saveOrUpdate(usuarioBuscado);
    }

    @Override
    public Usuario getUsuarioById(Long id) {
        String hql = "SELECT u FROM Usuario u LEFT JOIN FETCH u.reto WHERE u.id = :id";
        return this.sessionFactory.getCurrentSession()
                .createQuery(hql, Usuario.class)
                .setParameter("id", id)
                .uniqueResult();
    }

    @Override
    public List<Reto> getRetosDeUsuario(Usuario usuario) {
        Usuario usuarioConReto = this.getUsuarioById(usuario.getId());
        return usuarioConReto.getRetos();
    }


}
