package com.inventario.projeto.service.impl;

import com.inventario.projeto.exception.NotFoundException;
import com.inventario.projeto.mapper.ItemMapper;
import com.inventario.projeto.model.Categoria;
import com.inventario.projeto.model.Item;
import com.inventario.projeto.payload.DTO.ItemDTO;
import com.inventario.projeto.payload.Response;
import com.inventario.projeto.payload.ResponseSistemaABC;
import com.inventario.projeto.repositories.CategoriaRepository;
import com.inventario.projeto.repositories.ItemRepository;
import com.inventario.projeto.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final CategoriaRepository categoriaRepository;
    private final ItemMapper itemMapper;

    @Override
    public Response<ItemDTO> findAll(Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarItemsPor, String ordem) {

        Sort sort = ordem.equalsIgnoreCase("asc")
                ? Sort.by(ordenarItemsPor).ascending()
                : Sort.by(ordenarItemsPor).descending();

        PageRequest paginacao = PageRequest.of(numeroDaPagina, tamanhoDaPagina, sort);
        Page<Item> pagina = itemRepository.findAll(paginacao);

        List<Item> items = pagina.getContent();

        return Response.<ItemDTO>builder()
                .content(itemMapper.toItemDTOs(items))
                .numeroDaPagina(pagina.getNumber())
                .tamanhoDaPagina(pagina.getSize())
                .totalDeElementos(pagina.getTotalElements())
                .totalDePaginas(pagina.getTotalPages())
                .ultimaPagina(pagina.isLast())
                .build();
    }

    @Override
    public Response<ItemDTO> findItemsEmAlerta(Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarItemsPor, String ordem) {
        Sort sort = ordem.equalsIgnoreCase("asc")
                ? Sort.by(ordenarItemsPor).ascending()
                : Sort.by(ordenarItemsPor).descending();

        PageRequest paginacao = PageRequest.of(numeroDaPagina, tamanhoDaPagina, sort);
        Page<Item> pagina = itemRepository.findItemEmAlerta(paginacao);

        List<Item> items = pagina.getContent();

        return Response.<ItemDTO>builder()
                .content(itemMapper.toItemDTOs(items))
                .numeroDaPagina(pagina.getNumber())
                .tamanhoDaPagina(pagina.getSize())
                .totalDeElementos(pagina.getTotalElements())
                .totalDePaginas(pagina.getTotalPages())
                .ultimaPagina(pagina.isLast())
                .build();
    }

    @Override
    public ResponseSistemaABC findItemsSistemaABC(Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarItemsPor, String ordem) {
        Sort sort = ordem.equalsIgnoreCase("asc")
                ? Sort.by("valorGanho").ascending()
                : Sort.by("valorGanho").descending();

        PageRequest paginacao = PageRequest.of(numeroDaPagina, tamanhoDaPagina, sort);
        Page<Item> pagina = itemRepository.findAll(paginacao);

        List<Item> items = pagina.getContent();

        List<Item> A = new ArrayList<>();
        List<Item> B = new ArrayList<>();
        List<Item> C = new ArrayList<>();

        double porcentagemAcumulada = 0;
        for (Item item : items) {
            double porcentagem = item.getValorGanho() / itemRepository.valorTotal() * 100;

            porcentagemAcumulada += porcentagem;

            if (porcentagemAcumulada <= 80.0) {
                A.add(item);
            } else if (porcentagemAcumulada <= 95.0) {
                B.add(item);
            } else {
                C.add(item);
            }
        }



        return ResponseSistemaABC.builder()
                .itemsA(itemMapper.toItemDTOs(A))
                .itemsB(itemMapper.toItemDTOs(B))
                .itemsC(itemMapper.toItemDTOs(C))
                .numeroDaPagina(pagina.getNumber())
                .tamanhoDaPagina(pagina.getSize())
                .totalDeElementos(pagina.getTotalElements())
                .totalDePaginas(pagina.getTotalPages())
                .ultimaPagina(pagina.isLast())
                .build();
    }

    @Override
    public ItemDTO addItem(ItemDTO itemDTO, Integer categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new NotFoundException("Categoria", categoriaId));
        Item item = itemMapper.toItem(itemDTO);
        item.setCategoria(categoria);

        item = itemRepository.save(item);
        return itemMapper.toItemDTO(item);
    }

    @Override
    public ItemDTO updateItem(Integer id, ItemDTO itemDTO) {
        Item itemFromDB = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item", id));
        Item itemToBeUpdated = itemMapper.toItem(itemDTO);

        if (itemToBeUpdated.getSKU() == null) itemToBeUpdated.setSKU(itemFromDB.getSKU());
        if (itemToBeUpdated.getCodigoDeBarras() == null) itemToBeUpdated.setCodigoDeBarras(itemFromDB.getCodigoDeBarras());
        if (itemToBeUpdated.getNome() == null) itemToBeUpdated.setNome(itemFromDB.getNome());
        if (itemToBeUpdated.getDescricao() == null) itemToBeUpdated.setDescricao(itemFromDB.getDescricao());
        if (itemToBeUpdated.getMarca() == null) itemToBeUpdated.setMarca(itemFromDB.getMarca());
        if (itemToBeUpdated.getQuantidadeMinima() == null) itemToBeUpdated.setQuantidadeMinima(itemFromDB.getQuantidadeMinima());
        if (itemToBeUpdated.getPeso() == null) itemToBeUpdated.setPeso(itemFromDB.getPeso());
        if (itemToBeUpdated.getPrecoCompra() == null) itemToBeUpdated.setPrecoCompra(itemFromDB.getPrecoCompra());
        if (itemToBeUpdated.getPrecoVenda() == null) itemToBeUpdated.setPrecoVenda(itemFromDB.getPrecoVenda());
        if (itemToBeUpdated.getTaxa() == null) itemToBeUpdated.setTaxa(itemFromDB.getTaxa());
        if (itemToBeUpdated.getAtivo() == null) itemToBeUpdated.setAtivo(itemFromDB.getAtivo());

        itemToBeUpdated.setCategoria(itemFromDB.getCategoria());
        itemToBeUpdated.setUltimoUpdate(LocalDate.now());
        itemToBeUpdated.setQuantidadeEmEstoque(itemFromDB.getQuantidadeEmEstoque());
        itemToBeUpdated.setCriadoEm(itemFromDB.getCriadoEm());
        itemToBeUpdated.setId(itemFromDB.getId());

        Item itemUpdated = itemRepository.save(itemToBeUpdated);
        return itemMapper.toItemDTO(itemUpdated);
    }

    @Override
    public ItemDTO deleteItem(Integer id) {
        Item itemFromDB = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item", id));

        itemRepository.deleteById(id);
        return itemMapper.toItemDTO(itemFromDB);
    }
}
