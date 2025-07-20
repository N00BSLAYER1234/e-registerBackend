package it.realeites.learning.registroelettronicobackend.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import it.realeites.learning.registroelettronicobackend.model.entity.Giustificativo;
import it.realeites.learning.registroelettronicobackend.repository.GiustificativoRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test per GiustificativoServiceImpl")
class GiustificativoServiceImplTest {

    @Mock
    private GiustificativoRepository giustificativoRepository;

    @InjectMocks
    private GiustificativoServiceImpl giustificativoService;

    private Giustificativo giustificativoMock;

    @BeforeEach
    void init() {


        giustificativoMock = new Giustificativo();
        giustificativoMock.setId(1);
        giustificativoMock.setDescrizione("Visita medica");
        giustificativoMock.setAccettata(false);
    }

    @Test
    @DisplayName("trova giustificativo")
    void testFindById() {
        when(giustificativoRepository.findById(1)).thenReturn(Optional.of(giustificativoMock));

        Optional<Giustificativo> result = giustificativoService.findById(1);

        assertTrue(result.isPresent());
        assertEquals(giustificativoMock.getId(), result.get().getId());
        verify(giustificativoRepository).findById(1);
    }

    @Test
    @DisplayName("ritorna empty per giustificativo non trovato")
    void testFindByIdNotFound() {
        when(giustificativoRepository.findById(999)).thenReturn(Optional.empty());

        Optional<Giustificativo> result = giustificativoService.findById(999);

        assertFalse(result.isPresent());
        verify(giustificativoRepository).findById(999);
    }

    @Test
    @DisplayName("ritorna tutti i giustificativi")
    void testFindAll() {
        List<Giustificativo> giustificativi = Arrays.asList(giustificativoMock);
        when(giustificativoRepository.findAll()).thenReturn(giustificativi);

        List<Giustificativo> result = giustificativoService.findAll();

        assertEquals(1, result.size());
        assertEquals(giustificativoMock.getId(), result.get(0).getId());
        verify(giustificativoRepository).findAll();
    }

    @Test
    @DisplayName("salva giustificativo")
    void testSave() {
        when(giustificativoRepository.save(giustificativoMock)).thenReturn(giustificativoMock);

        Giustificativo result = giustificativoService.save(giustificativoMock);

        assertEquals(giustificativoMock.getId(), result.getId());
        verify(giustificativoRepository).save(giustificativoMock);
    }

    @Test
    @DisplayName("accetta giustificativo")
    void testSetAccettato() {
        when(giustificativoRepository.findById(1)).thenReturn(Optional.of(giustificativoMock));
        when(giustificativoRepository.save(any(Giustificativo.class))).thenReturn(giustificativoMock);

        assertDoesNotThrow(() -> giustificativoService.setAccettato(1));
        verify(giustificativoRepository).findById(1);
        verify(giustificativoRepository).save(any(Giustificativo.class));
    }
}