package com.journey_back.service;

import com.journey_back.infra.exception.ValidationError;
import com.journey_back.infra.security.TokenService;
import com.journey_back.model.TripModel;
import com.journey_back.model.UserModel;
import com.journey_back.repository.TripRepository;
import com.journey_back.repository.UserRepository;
import com.journey_back.request.TripRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    //Construtor
    public TripService(TripRepository repository) {
        this.tripRepository = repository;
    }

    // Pegar token do cabeçalho Authorization
    public String processarComAuthorization() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        } else {
            throw new RuntimeException("Valor do cabeçalho é nulo");
        }
    }

    // Pegar id do usuario logado
    public Integer pegarIdUsuario() {
        var subject = tokenService.getSubject(processarComAuthorization());
        UserModel user = (UserModel) userRepository.findByEmail(subject);
        var  usuarioID = user.getId();
        return usuarioID;
    }


    // Todas as viagens
    public List<TripModel> getTripList() {
        Integer idUser = pegarIdUsuario();
        return tripRepository.findByUserId(idUser);
    }

    // Viagem especifica
    public TripModel getTripDetails(Integer id) {
        Optional<TripModel> trip = this.tripRepository.findById(id);
        if(trip.isPresent()) {
            TripModel tripDetail = trip.get();
            return tripDetail;
        } else {
            throw new ValidationError("Esta viagem nao existe");
        }
    }

    // Cadastrar uma viagem
    public TripModel registerTrip(TripRequest tripRequest) {
        Integer idUsuario = pegarIdUsuario();
        UserModel usuarioLogado = new UserModel();

        Optional<UserModel> user = userRepository.findById(idUsuario);
        if (user.isPresent()) {
            usuarioLogado = user.get();
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        TripModel trip =  new TripModel();
        trip.setDestination(tripRequest.destination());
        trip.setStartsAt(LocalDate.parse(tripRequest.startsAt(), formatter));
        trip.setEndsAt(LocalDate.parse(tripRequest.endsAt(), formatter));
        trip.setIsConfirmed(false);
        trip.setOwnerName(usuarioLogado.getName());
        trip.setUserId(idUsuario);

        tripRepository.save(trip);

        return trip;
    }

    //Atualizar viagem
    public TripModel updateTrip(Integer id, TripRequest request) {
        Optional<TripModel> trip = this.tripRepository.findById(id);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (trip.isPresent()) {
            TripModel rawTrip = trip.get();
            rawTrip.setStartsAt(LocalDate.parse(request.startsAt(), formatter));
            rawTrip.setEndsAt(LocalDate.parse(request.endsAt(), formatter));
            rawTrip.setDestination(request.destination());

            this.tripRepository.save(rawTrip);

            return rawTrip;
        } else {
            throw new ValidationError("Viagem nao econtrada");
        }
    }

    // Deletar viagem
    public boolean deleteTrip(Integer id)  {
        var trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            tripRepository.deleteById(id);
            return true;
        } else {
            throw new ValidationError("Viagem nao econtrada");
        }
    }
}
