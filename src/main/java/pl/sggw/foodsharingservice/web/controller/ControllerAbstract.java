package pl.sggw.foodsharingservice.web.controller;

import org.springframework.data.domain.PageRequest;

public class ControllerAbstract {
//    //  TODO add links
//    protected <T> FacingPage<T> convertToFacingPage(Page<T> serviceStatusPage) {
//        return new FacingPage<>(
//                serviceStatusPage.getContent(),
//                PageMetaData.builder()
//                        .pageNumber(serviceStatusPage.getNumber() + 1)
//                        .pageSize(serviceStatusPage.getSize())
//                        .totalCount(serviceStatusPage.getTotalElements())
//                        //                    .pageLinks(pageLinks)
//                        .build());
//    }

    protected PageRequest preparePageRequest(int page, int pageSize) {
        return PageRequest.of(page - 1, pageSize);
    }
}
