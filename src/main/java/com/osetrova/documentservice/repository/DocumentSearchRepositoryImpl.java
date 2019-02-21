package com.osetrova.documentservice.repository;

import com.osetrova.documentservice.dto.DocumentSearchParameter;
import com.osetrova.documentservice.model.Document;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DocumentSearchRepositoryImpl implements DocumentSearchRepository {

    private static final String FORMAT_FOR_SEARCH = "%%%s%%";
    private static final String TYPE = "type";
    private static final String TITLE = "title";
    private EntityManager entityManager;

    @Override
    public List<Document> searchBy(DocumentSearchParameter parameter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Document> criteria = cb.createQuery(Document.class);
        Root<Document> documentRoot = criteria.from(Document.class);

        List<Predicate> searchParameters = getSearchParameters(parameter, cb, documentRoot);

        criteria.select(documentRoot)
                .where(
                    searchParameters.toArray(new Predicate[]{})
                );
        return entityManager.createQuery(criteria)
                .getResultList();
    }

    private List<Predicate> getSearchParameters(DocumentSearchParameter parameter, CriteriaBuilder cb,
                                       Root<Document> documentRoot) {
        List<Predicate> searchParameters = new ArrayList<>();
        if (isNotBlank(parameter.getType())) {
            searchParameters.add(cb.equal(documentRoot.get(TYPE), parameter.getType()));
        }
        if (isNotBlank(parameter.getTitle())) {
            searchParameters.add(cb.like(documentRoot.get(TITLE), format(FORMAT_FOR_SEARCH, parameter.getTitle())));
        }

        return searchParameters;
    }
}
