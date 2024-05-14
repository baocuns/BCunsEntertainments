let isModalOpenChapter = false;
let isCallApiGetChapters = false;
let infoChapter = $('#info-chapter');
let infoComic = $('#info-comic');

$(document).ready(function () {
    // call api to get chapters
    if (!isCallApiGetChapters) {
        handleCallApiGetChapters();
    }
    $(document).on('click', '#btn-modal-chapters', function () {
        handleOpenModalChapters();
    });
    $(document).on('click', '.close', function () {
        handleCloseModalChapters();
    });
    // Khi người dùng nhấn bất kỳ đâu ngoài modal, modal cũng sẽ đóng
    $(document).on('click', function (e) {
        if (!$(e.target).closest('#btn-modal-chapters').length && $(e.target).is('#chapter-modal') && isModalOpenChapter) {
            handleCloseModalChapters();
        }
    });
    // ẩn modal khi bấm nút back
    window.addEventListener('popstate', function(event) {
        if (!isModalOpenChapter) history.back();
        // Kiểm tra trạng thái modal
        if (isModalOpenChapter) {
            handleCloseModalChapters()
        }
    });
    // chuyển đỗi giữa các taps list chapter
    $(document).on('click', '.btn-taps-chapter', function () {
        tabsChapter($(this).attr('id'))
    });
    $('img').on('error', function () {
        handleErrorPicture($(this));
    });
    $('.picture').each(function () {
        $(this).find('img').on('loadeddata', function () {
            $(this).parent().find('.picture-load').remove();
        });
    })
});
const handleErrorPicture = (img) => {
    const server = img.data('server');
    const index = img.data('index');
    if (server) {
        const servers = server.slice(1, -1).split(', ').map(s => s.trim());
        img.attr('src', `https://picture.bcent.online/images?src=${servers[index]}`);
        img.data('index', index < servers.length - 1 ? index + 1 : 0);
    }
}
const handleOpenModalChapters = () => {
    $('#modal-chapters-parent').removeClass('hidden').addClass('fixed');
    setTimeout(() => {
        $('#chapter-modal').removeClass('opacity-0').addClass('opacity-100');
        $('#chapter-list').removeClass('opacity-0').addClass('opacity-100');
    }, 100);
    // scroll to top view
    $('#chapter-current')
        .addClass('bg-red-200 dark:bg-green-200')
        .get(0)
        .scrollIntoView({behavior: "smooth", block: "center", inline: "nearest"});
    // add history
    history.pushState({modalOpenChapter: true}, null, null);
    isModalOpenChapter = true;
}
const handleCloseModalChapters = () => {
    $('#chapter-list').removeClass('opacity-100').addClass('opacity-0');
    setTimeout(() => {
        $('#chapter-modal').removeClass('opacity-100').addClass('opacity-0');
    }, 200);
    setTimeout(() => {
        $('#modal-chapters-parent').removeClass('fixed').addClass('hidden');
    }, 400);
    isModalOpenChapter = false;
}
const tabsChapter = (id) => {
    const content = $(`#list-chapter-${id}`)
    const tabs = $('.tabs-chapter')
    tabs.addClass('hidden')
    content.removeClass('hidden')
}
const handleCallApiGetChapters = () => {
    if (!infoComic.data('comic-id')) return;
    $.post('/api/comics/chapters', {
        comicId: infoComic.data('comic-id'),
        chunkSize: 100
    })
        .done(function (data) {
            if (data.length > 0 && data[0].length > 0) {
                // add button next and prev
                let objChapterPrevAndNext = handleGetChapterPrevAndNextInChapters(data);
                if (objChapterPrevAndNext.chapterCurrent) {
                    // add link back to comic
                    $('#link-back-to-comic')
                        .attr('href', `/comics/${handleTextToSlug(infoComic.text())}-${infoComic.data('comic-id')}`)
                        .append(`<span class="hover:underline decoration-red-500 dark:decoration-green-500">${infoComic.text()}</span>`);
                    // add button current chapter
                    $('#div-modal-chapter').append(`<button
                                id="btn-modal-chapters"
                                class="shadow py-1 px-2 rounded dark:shadow-slate-700 dark:hover:bg-slate-700 hover:bg-slate-100">
                            <p class="line-clamp-1 font-bold text-sm md:text-xl dark:text-white">${objChapterPrevAndNext.chapterCurrent.title}</p>
                        </button>`);
                }
                if (objChapterPrevAndNext.chapterPrev) {
                    $('#btn-prev-chapter').append(`<a class="shadow py-1 px-2 rounded text-md md:text-xl text-red-500 dark:text-green-500 dark:shadow-slate-700 dark:hover:bg-slate-700 hover:bg-slate-100"
                               href="/comics/${handleTextToSlug(infoComic.text())}-${infoComic.data('comic-id')}/chapter/${objChapterPrevAndNext.chapterPrev.id}">
                                <i class="fa-light fa-angles-left"></i>
                            </a>`);
                }
                if (objChapterPrevAndNext.chapterNext) {
                    $('#btn-next-chapter').append(`<a class="shadow py-1 px-2 rounded text-md md:text-xl text-red-500 dark:text-green-500 dark:shadow-slate-700 dark:hover:bg-slate-700 hover:bg-slate-100"
                               href="/comics/${handleTextToSlug(infoComic.text())}-${infoComic.data('comic-id')}/chapter/${objChapterPrevAndNext.chapterNext.id}">
                                <i class="fa-light fa-angles-right"></i>
                            </a>`);
                }
                // add chapter
                $('#chapter-taps-list').empty();
                data.forEach((chunk, index) => {
                    // list taps chapter
                    $('#btn-taps-list').append(`<button
                        id="${chunk[0].id}"
                        type="button"
                        class="btn-taps-chapter md:px-2 transition duration-500 ease-in-out hover:scale-125 hover:bg-red-300 text-red-500 font-semibold shadow rounded w-full py-2 dark:hover:bg-green-400 dark:text-white bg-red-200 dark:bg-green-800"
                        >
                        <p>${chunk[0].chapter} - ${chunk[chunk.length - 1].chapter}</p>
                    </button>`)
                    // list chapter
                    let htmlChunk = '';
                    chunk.forEach(chapter => {
                        htmlChunk += `<a href="/comics/${handleTextToSlug(infoComic.text())}-${infoComic.data('comic-id')}/chapter/${chapter.id}">
                            <div id="${chapter.id === objChapterPrevAndNext.chapterCurrent.id ? 'chapter-current' : ''}" class="flex justify-center p-2 rounded shadow hover:scale-105 hover:bg-gray-200 cursor-pointer duration-500 ease-in-out dark:shadow-gray-700 dark:hover:bg-slate-700">
                                <h1 class="flex items-center text-sm md:text-base col-span-2 md:col-auto">${chapter.title}</h1>
                            </div>
                        </a>`
                    })
                    // add chunk
                    $('#chapter-taps-list').append(
                        `<div id="list-chapter-${chunk[0].id}" class="grid grid-cols-4 gap-4 dark:text-slate-400 tabs-chapter ${index === objChapterPrevAndNext.chunkIndex ? '' : 'hidden'}">
                        ${htmlChunk}
                    </div>`
                    )
                })
                isCallApiGetChapters = true;
            } else {
                $('#chapter-taps-list').empty().append('<div class="font-medium p-4 border-l-2 border-red-500 dark:text-white dark:border-green-500">Không có chương nào Fen ơi!</div>')
            }
        });
}
// handle get chapter prev and next in chapters: List<List<Chapter>> chapters to object {chapterPrev, chapterNext}
const handleGetChapterPrevAndNextInChapters = (chapters) => {
    let objChapterPrevAndNext = {
        chapterPrev: null,
        chapterNext: null,
        chapterCurrent: null,
        chunkIndex: 0
    };
    chapters.forEach((chunk, idx) => {
        chunk.forEach((chapter, index) => {
            if (chapter.id === infoChapter.data('chapter-id')) {
                objChapterPrevAndNext.chapterCurrent = chapter;
                objChapterPrevAndNext.chunkIndex = idx;
                if (index < chunk.length - 1) {
                    objChapterPrevAndNext.chapterPrev = chunk[index + 1];
                }
                if (index > 0) {
                    objChapterPrevAndNext.chapterNext = chunk[index - 1];
                }
            }
        })
    })
    return objChapterPrevAndNext;
}