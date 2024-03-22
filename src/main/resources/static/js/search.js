$(document).ready(function () {
    const modalSearch = $("#modal-search");
    const modalContentSearch = $("#modal-content-search");
    const overlaySearch = $("#overlay-search");
    const overlayBodySearch = $("#overlay-body-search");
    const openModalBtnSearch = $("#btn-open-modal-search");
    const closeModalBtnSearchMobile = $("#btn-open-modal-search-mobile");
    const inputSearch = $("#search-input");
    const btnInputSearch = $("#btn-input-search");
    const listSearch = $("#list-search");
    let isCallApi = false;
    let isLoaded = false;
    let timeout = null;
    let isModalOpenSearch = false;

    // handle isCallApi on change
    const handleIsCallApiOnChange = function () {
        if (isCallApi) {
            if (isLoaded) {
                return;
            }
            btnInputSearch.html('<svg class="animate-spin mr-3 h-5 w-5 text-green-500" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"> <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle> <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path> </svg>');
            isLoaded = true;
        } else {
            btnInputSearch.html('<i class="fa-light fa-magnifying-glass fa-lg"></i>');
            isLoaded = false;
        }
    };
    // switch icon search
    inputSearch.on("input", () => {
        isCallApi = inputSearch.val().length > 0;
        handleIsCallApiOnChange();
    });

    const openModalSearch = function () {
        modalSearch.removeClass("hidden").addClass("flex");
        overlaySearch.removeClass("hidden");
        inputSearch.focus();
        setTimeout(function () {
            overlayBodySearch.addClass("active");
            modalContentSearch.addClass("active");
        }, 100);
        history.pushState({ modalOpenSearch: true }, null, null);
        isModalOpenSearch = true;
    };
    openModalBtnSearch.on("click", openModalSearch);
    closeModalBtnSearchMobile.on("click", openModalSearch);
    // ẩn modal khi bấm nút back
    window.addEventListener('popstate', function(event) {
        // Kiểm tra trạng thái modal
        if (isModalOpenSearch) {
            closeModalSearch()
        }
    });

    const closeModalSearch = function () {
        modalContentSearch.removeClass("active");
        overlayBodySearch.removeClass("active");
        setTimeout(function () {
            modalSearch.addClass("hidden");
            overlaySearch.addClass("hidden");
        }, 400);
        isModalOpenSearch = false;
    };
    overlaySearch.on("click", closeModalSearch);

    // input onchange
    inputSearch.on("input", function () {
        if (timeout) {
            clearTimeout(timeout);
        }
        if (inputSearch.val().length > 0) {
            timeout = setTimeout(function () {
                // call api
                isCallApi = true;
                $.post("/api/comics/search", {
                    title: inputSearch.val(),
                }, (data, status) => {
                    isCallApi = false;
                    handleIsCallApiOnChange();
                    // set list search empty
                    listSearch.empty();
                    listSearch.removeClass('border-t')
                    // update list search
                    if (data.length > 0) {
                        listSearch.addClass('border-t')
                        data.forEach(comic => {
                            listSearch.append('<li class="hover:bg-gray-200" role="option"> ' +
                                `<a href="/comics/${comic.titleSlug}" class="grid grid-cols-5 items-center justify-between p-4 gap-2"> ` +
                                '<div class="col-span-3"> ' +
                                `<span class="font-semibold text-slate-900 line-clamp-2">${comic.title}</span> ` +
                                '</div> ' +
                                '<div class="col-span-2 flex gap-2 items-center justify-between"> ' +
                                '<div class="flex items-center gap-2"><span class="text-slate-600"><i class="fa-light fa-file-image"></i></span> ' +
                                `<span class="text-slate-600">${comic.countChapter}</span> </div>` +
                                `<span class="text-right text-slate-600">${comic.timeAgo}</span> ` +
                                `<span><i class="fa-light fa-face-grin-hearts"></i> ${comic.view}</span>` +
                                '</div> ' +
                                '</a> ' +
                                '</li>');
                        })
                    }
                })
            }, 1000);
        }
    });
});