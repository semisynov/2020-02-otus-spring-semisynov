package ru.semisynov.otus.spring.homework13.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.semisynov.otus.spring.homework13.errors.BadParameterException;
import ru.semisynov.otus.spring.homework13.model.Book;
import ru.semisynov.otus.spring.homework13.model.Genre;
import ru.semisynov.otus.spring.homework13.model.enums.UserRole;
import ru.semisynov.otus.spring.homework13.repositories.GenreRepository;
import ru.semisynov.otus.spring.homework13.security.SecurityUserDetails;
import ru.semisynov.otus.spring.homework13.services.utils.BookAclPermission;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class BookAclService {

    private static final String KIDS_BOOK_GENRE = "Детский жанр";

    private final MutableAclService aclService;
    private final GenreRepository genreRepository;

    public void createAcl(Book book) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SecurityUserDetails user;
        if (principal instanceof SecurityUserDetails) {
            user = (SecurityUserDetails) principal;
        } else {
            throw new BadParameterException(String.format("Unknown user format %s", principal));
        }
        ObjectIdentity objectIdentity = new ObjectIdentityImpl(Book.class, book.getId());
        MutableAcl acl;
        try {
            acl = (MutableAcl) aclService.readAclById(objectIdentity);
        } catch (NotFoundException e) {
            acl = aclService.createAcl(objectIdentity);
        }
        Sid adminSid = new GrantedAuthoritySid(UserRole.ROLE_ADMIN.name());
        Sid userSid = new GrantedAuthoritySid(UserRole.ROLE_USER.name());
        Sid childSid = new GrantedAuthoritySid(UserRole.ROLE_CHILD.name());

        acl.insertAce(acl.getEntries().size(), BookAclPermission.READ_WRITE, userSid, true);
        acl.insertAce(acl.getEntries().size(), BookAclPermission.ALL_OPERATIONS, adminSid, true);
        acl.insertAce(
                acl.getEntries().size(), BasePermission.READ, childSid,
                book.getGenres().contains(getKidsGenreId()));
        aclService.updateAcl(acl);
    }

    private Genre getKidsGenreId() {
        Optional<Genre> kidsGenre = genreRepository.findByTitleIgnoreCase(KIDS_BOOK_GENRE);
        Genre kidsBookGenre = null;
        if (kidsGenre.isPresent()) {
            kidsBookGenre = kidsGenre.get();
        }
        return kidsBookGenre;
    }
}
