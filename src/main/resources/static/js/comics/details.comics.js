const infoComic = $('#info-comic')
$(document).ready(function () {
    // Hàm ẩn hiện modal
    let isModalOpenComicRating = false
    // open modal
    const handleOpenModalComicRating = () => {
        $('#modal-comic-rating').removeClass('hidden').addClass('flex')
        $('#overlay-comic-rating').removeClass('hidden')
        setTimeout(() => {
            $('#overlay-body-comic-rating').addClass('opacity-100').removeClass('opacity-0')
            $('#modal-content-comic-rating').addClass('opacity-100 translate-y-0').removeClass('opacity-0 translate-y-2.5')
        }, 100)
        history.pushState({modalComicRatingOpen: true}, null, null);
        isModalOpenComicRating = true
    }
    $('.rating-star').click(function () {
        // check is login
        if ($('.rating').data('is-login') === false) {
            window.location.href = '/login'
        } else {
            // check is rating
            if ($('.rating').data('is-rating') === false) {
                handleOpenModalComicRating()
            } else {
                alert('Bạn đã đánh giá truyện này')
            }
        }
    })
    // close modal
    const handleModalComicRatingClose = () => {
        $('#modal-content-comic-rating').removeClass('opacity-100 translate-y-0').addClass('opacity-0 translate-y-2.5')
        $('#overlay-body-comic-rating').removeClass('opacity-100').addClass('opacity-0')
        setTimeout(() => {
            $('#modal-comic-rating').removeClass('flex').addClass('hidden')
            $('#overlay-comic-rating').addClass('hidden')
        }, 400)
        isModalOpenComicRating = false
    }
    $('#overlay-comic-rating').click(handleModalComicRatingClose)
    $('#btn-close-modal-comic-rating').click(handleModalComicRatingClose)
    // close modal when press esc
    $(document).keydown(function (e) {
        if (e.key === 'Escape') {
            handleModalComicRatingClose()
        }
    })
    // close modal when press back
    window.onpopstate = function (event) {
        if (event.state === null) {
            if (isModalOpenComicRating) {
                handleModalComicRatingClose()
            }
        } else if (event.state.modalProfileEditOpen) {
            handleModalComicRatingClose()
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // script modal list user rating
    // Hàm ẩn hiện modal
    let isModalOpenListUserRating = false
    let isCallApi = false
    // open modal
    const handleOpenModalListUserRating = () => {
        $('#modal-list-user-rating').removeClass('hidden').addClass('flex')
        $('#overlay-list-user-rating').removeClass('hidden')
        setTimeout(() => {
            $('#overlay-body-list-user-rating').addClass('opacity-100').removeClass('opacity-0')
            $('#modal-content-list-user-rating').addClass('opacity-100 translate-y-0').removeClass('opacity-0 translate-y-2.5')
            setTimeout(() => {
                $('#modal-list-user-rating').removeClass('overflow-hidden').addClass('overflow-y-auto')
            }, 300)
        }, 100)
        history.pushState({modalListUserRatingOpen: true}, null, null);
        isModalOpenListUserRating = true

        // get all user rating from backend
        isCallApi = true
        $.post('/api/ratings/comic/all', {comicId: $('#info-comic').data('comic-id')}, function (data, status) {
            if (status === 'success') {
                $('#list-user-rating').empty()
                data.forEach((item) => {
                    $('#list-user-rating').append(`
                            <div class="flex items-center gap-4">
                                <img
                                        src="${item.userProfileAvatarUrl}"
                                        alt="avatar"
                                        class="h-10 w-10 rounded-full object-cover"
                                />
                                <div class="">
                                    <div class="flex gap-2">
                                        <p class="font-medium">${item.userProfileFullname}</p>
                                        <p>${item.rate}/5</p>
                                        <svg class="text-yellow-500 h-5 w-5" viewBox="0 0 20 20"
                                             fill="currentColor" aria-hidden="true">
                                            <path fill-rule="evenodd"
                                                  d="M10.868 2.884c-.321-.772-1.415-.772-1.736 0l-1.83 4.401-4.753.381c-.833.067-1.171 1.107-.536 1.651l3.62 3.102-1.106 4.637c-.194.813.691 1.456 1.405 1.02L10 15.591l4.069 2.485c.713.436 1.598-.207 1.404-1.02l-1.106-4.637 3.62-3.102c.635-.544.297-1.584-.536-1.65l-4.752-.382-1.831-4.401z"
                                                  clip-rule="evenodd"/>
                                        </svg>
                                    </div>
                                    <small>${item.content}</small>
                                </div>
                            </div>
                            `)
                })
            }
        })
    }
    $('.list-user-rating').click(function () {
        handleOpenModalListUserRating()
    })
    // close modal
    const handleModalListUserRatingClose = () => {
        $('#modal-list-user-rating').addClass('overflow-hidden').removeClass('overflow-y-auto')
        $('#modal-content-list-user-rating').removeClass('opacity-100 translate-y-0').addClass('opacity-0 translate-y-2.5')
        $('#overlay-body-list-user-rating').removeClass('opacity-100').addClass('opacity-0')
        setTimeout(() => {
            $('#modal-list-user-rating').removeClass('flex').addClass('hidden')
            $('#overlay-list-user-rating').addClass('hidden')
        }, 400)
        isModalOpenListUserRating = false
    }
    $('#overlay-list-user-rating').click(handleModalListUserRatingClose)
    $('#btn-close-modal-list-user-rating').click(handleModalListUserRatingClose)
    // close modal when press esc
    $(document).keydown(function (e) {
        if (e.key === 'Escape') {
            handleModalListUserRatingClose()
        }
    })
    // close modal when press back
    window.onpopstate = function (event) {
        if (event.state === null) {
            if (isModalOpenListUserRating) {
                handleModalListUserRatingClose()
            }
        } else if (event.state.modalProfileEditOpen) {
            handleModalListUserRatingClose()
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Ẩn màu sao khi hover
    $('.rating').hover(function () {
        $('.rating-star').each(function () {
            $(this).hasClass('text-yellow-500') ? $(this).addClass('act') : ''
            $(this).removeClass('text-yellow-500')
            $(this).addClass('text-gray-200')
        })
    }, function () {
        $('.rating-star').each(function () {
            $(this).hasClass('act') ? $(this).addClass('text-yellow-500') : ''
            $(this).removeClass('act')
        })
    })
    // set star rating from backend data avgRating
    const avgRating = $('#avgRating').text()
    const rating = Math.round(avgRating)
    $('.rating-star').each(function (index) {
        if (index > 4 - rating) {
            $(this).removeClass('text-gray-200')
            $(this).addClass('text-yellow-500')
        }
        // set rate value when click star
        $(this).click(function () {
            $('#rate').val($(this).data('rate'))
        })
    })

    // chuyển đỗi giữa các taps list chapter
    $(document).on('click', '.btn-taps-chapter', function () {
        tabsChapter($(this).attr('id'))
    })
    // load chapters
    $.post('/api/comics/chapters', {comicId: $('#info-comic').data('comic-id')})
        .done(function (data) {
            if (data.length > 0 && data[0].length > 0) {
                // add button read chapter
                if (data.length >= 1) {
                    $('#btn-read-now').append(
                        `<a href="/comics/${handleTextToSlug($('#info-comic').text())}-${$('#info-comic').data('comic-id')}/chapter/${data[data.length - 1][data[data.length - 1].length - 1].id}">
                        <div class="p-2 bg-transparent hover:scale-110 duration-300 ease-in-out rounded text-black dark:text-gray-300 flex items-center border border-green-500">
                            Đọc Ngay
                        </div>
                    </a>`
                    )
                    $('#btn-read-update').append(
                        `<a href="/comics/${handleTextToSlug($('#info-comic').text())}-${$('#info-comic').data('comic-id')}/chapter/${data[0][0].id}">
                        <div class="p-2 bg-transparent hover:scale-110 duration-300 ease-in-out rounded text-black dark:text-gray-300 flex items-center border border-red-500">
                            Chương Mới Nhất
                        </div>
                    </a>`
                    )
                }
                // add chapter
                $('#chapter-taps-list').empty();
                data.forEach((chunk, index) => {
                    // list taps chapter
                    $('#btn-taps-list').append(
                        `<button
                        id="${chunk[0].id}"
                        type="button"
                        class="btn-taps-chapter transition duration-500 ease-in-out hover:scale-125 hover:bg-red-300 text-red-500 font-semibold shadow rounded w-full py-2 dark:hover:bg-green-400 dark:text-white bg-red-200 dark:bg-green-800"
                        >
                        <p>${chunk[0].chapter} - ${chunk[chunk.length - 1].chapter}</p>
                    </button>`
                    )
                    // list chapter
                    let htmlChunk = '';
                    chunk.forEach(chapter => {
                        htmlChunk += `<a href="/comics/${handleTextToSlug($('#info-comic').text())}-${$('#info-comic').data('comic-id')}/chapter/${chapter.id}">
                            <div class="grid grid-cols-4 gap-2 p-2 my-2 rounded shadow hover:scale-105 hover:bg-gray-200 cursor-pointer duration-500 ease-in-out dark:shadow-gray-700 dark:hover:bg-slate-700">
                                <h1 class="flex items-center text-sm md:text-base col-span-2 md:col-auto">${chapter.title}</h1>
                                <div class="font-thin flex items-center"><i>${chapter.timeAgo}</i></div>
                                <div class="flex items-center gap-2">
                                    <p>${chapter.view}</p>
                                    <i class="fa-sharp fa-light fa-eyes"></i>
                                </div>
                            </div>
                        </a>`
                    })
                    // add chunk
                    $('#chapter-taps-list').append(
                        `<div id="list-chapter-${chunk[0].id}" class="dark:text-slate-400 tabs-chapter ${index > 0 ? 'hidden' : ''}">
                        ${htmlChunk}
                    </div>`
                    )
                })
            } else {
                $('#chapter-taps-list').empty();
                $('#chapter-taps-list').append('<div class="font-medium p-4 border-l-2 border-red-500 dark:text-white dark:border-green-500">Không có chương nào Fen ơi!</div>')
            }
        });
    // add comic to history
    handleAddComicToHistory()
});

const handleAddComicToHistory = () => {
    const comic = {
        id: infoComic.data('comic-id'),
        title: infoComic.text(),
        thumbnail: infoComic.data('comic-thumbnail'),
        slug: infoComic.data('comic-slug'),
        countChapter: infoComic.data('comic-count-chapter'),
        timeUpdate: infoComic.data('comic-time-ago'),
        timeRead: Date.now(),
        chapterRead: 0,
    }
    let comicHistory = localStorage.getItem("comicHistory");
    // get user
    const user = JSON.parse(localStorage.getItem('virus'));
    // history object
    const history = {
        userId: user ? user.bcId : null,
        comics: [comic]
    };

    if (comicHistory != null) {
        let histories = JSON.parse(comicHistory);
        if (user) {
            let historyByUser = histories.filter(history => history.userId === user.bcId);
            if (historyByUser.length  >0) {
                // comic
                let comicHistoryById = historyByUser[0].comics.filter(h => h.id === comic.id);
                // comics history
                let comicHistory = historyByUser[0].comics.filter(h => h.id !== comic.id);
                if (comicHistory.length >= 12) {
                    comicHistory.pop();
                }
                if (comicHistoryById.length > 0) {
                    // update time read
                    comicHistoryById[0].timeRead = comic.timeRead;
                    comicHistory.unshift(comicHistoryById[0])
                } else {
                    comicHistory.unshift(comic);
                }
                historyByUser[0].comics = comicHistory;
                histories = histories.filter(history => history.userId !== user.bcId);
                histories.push(historyByUser[0]);
            }
            else {
                histories.push(history);
            }
        } else {
            let historyByNull = histories.filter(history => history.userId === null);
            if (historyByNull.length  >0) {
                // comic
                let comicHistoryById = historyByNull[0].comics.filter(h => h.id === comic.id);
                // comics history
                let comicHistory = historyByNull[0].comics.filter(h => h.id !== comic.id);
                if (comicHistory.length >= 12) {
                    comicHistory.pop();
                }
                if (comicHistoryById.length > 0) {
                    // update time read
                    comicHistoryById[0].timeRead = comic.timeRead;
                    comicHistory.unshift(comicHistoryById[0])
                } else {
                    comicHistory.unshift(comic);
                }
                historyByNull[0].comics = comicHistory;
                histories = histories.filter(history => history.userId !== null);
                histories.push(historyByNull[0]);
            }
            else {
                histories.push(history);
            }
        }
        localStorage.setItem("comicHistory", JSON.stringify(histories));
    } else {
        localStorage.setItem("comicHistory", JSON.stringify([history]));
    }
}
const tabsChapter = (id) => {
    const content = $(`#list-chapter-${id}`)
    const tabs = $('.tabs-chapter')
    tabs.addClass('hidden')
    content.removeClass('hidden')
}