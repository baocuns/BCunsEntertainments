let comicId = $('#info-comic').data('comic-id');
let chapterId = $('#info-chapter').data('chapter-id');
let edt;
let page = 0;
let pageReply = 0;
let size = 10;
let isCallApiLoadComment = false;
let isCallApiSendComment = false;
let repliesOption = [];

$(document).ready(function () {
    // init editor
    handleCreateEditor();
    // ----------------- Event -----------------
    // Click outside the modal sticker range and it will be hidden
    $(document).click(function (e) {
        if (!$(e.target).closest('#modal-sticker').length &&
            !$(e.target).closest('#ck-editor-troll-face').length &&
            !$(e.target).closest('#ck-editor-onion').length &&
            !$(e.target).closest('#ck-editor-bafu').length &&
            !$(e.target).closest('#ck-editor-thobua').length &&
            !$(e.target).closest('#ck-editor-tuzki').length &&
            !$(e.target).closest('#ck-editor-yoyo').length &&
            !$(e.target).closest('#ck-editor-panda').length &&
            !$(e.target).closest('#ck-editor-baby').length &&
            !$(e.target).closest('#ck-editor-gif').length) {
            $('#modal-sticker').addClass('hidden');
        }
    });
    // Click on the sticker and it will be added to the editor
    $(document).on('click', '.btn-sticker', function () {
        handleAddSticker(edt, $(this).find('img').attr('src'));
    });
    $(document).on('click', '.btn-reply', function () {
        handleAddReply($(this).data('cmt-id'));
    });
    $(document).on('click', '#ck-editor-troll-face', function () {
        handleAppend(listTrollFace);
    });
    $(document).on('click', '#ck-editor-onion', function () {
        handleAppend(listOnion);
    });
    $(document).on('click', '#ck-editor-bafu', function () {
        handleAppend(listBafu);
    });
    $(document).on('click', '#ck-editor-thobua', function () {
        handleAppend(listThoBua);
    });
    $(document).on('click', '#ck-editor-tuzki', function () {
        handleAppend(listTuzki);
    });
    $(document).on('click', '#ck-editor-yoyo', function () {
        handleAppend(listYoyo);
    });
    $(document).on('click', '#ck-editor-panda', function () {
        handleAppend(listPanda);
    });
    $(document).on('click', '#ck-editor-baby', function () {
        handleAppend(listBaby);
    });
    $(document).on('click', '#ck-editor-gif', function () {
        handleAppend(listGif);
    });
    $(document).on('click', '#btn-comment-parent', function () {
        handleAddBoxCommentParent();
    });
    $(document).on('click', '#btn-comment-send', function () {
        handleSendComment();
    });
    $(document).on('click', '#btn-reply-send', function () {
        handleSendReply($(this).data('cmt-id'));
    });
    $(document).on('click', '#btn-comment-load-more', function () {
        handleCallApiCommentLoadMore();
    });
    $(document).on('click', '.btn-likes', function () {
        handleLikeComment($(this).data('cmt-id'));
    });
    $(document).on('click', '.btn-dislikes', function () {
        handleDislikeComment($(this).data('cmt-id'));
    });
    $(document).on('click', '.btn-deleted', function () {
        handleDeleteComment($(this).data('cmt-id'));
    });
    $(document).on('click', '.btn-reports', function () {
        handleReportComment($(this).data('cmt-id'));
    });
    // call api get comment
    handleCallApiGetComment();
    // load reply
    $(document).on('click', '.btn-load-reply', function () {
        handleLoadReply($(this).data('cmt-id'));
    });
});

//----------------------------------------------------------- Function
const handleSendReply = (cmtId) => {
    const content = $('.ck .ck-content');
    if (content.text() === '' || content === '' || content === '<p><br data-cke-filler="true"></p>') {
        alert('Nội dung không được để trống Fen ơi!');
        return;
    }
    if (content.text().length > 1000) {
        alert('Nội dung không được quá 1000 ký tự Fen ơi!');
        return;
    }
    if (!handleCheckLogin()) {
        alert('Phải đăng nhập đã Fen ơi!');
        return;
    }
    handleCallApiSendAddNewReply(comicId, chapterId, cmtId, content.html());
    handleLoaderOnchange();
}
const handleCallApiSendAddNewReply = (comicId, chapterId, cmtId, content) => {
    if (isCallApiSendComment) return;
    isCallApiSendComment = true;
    $.post('/api/comments/comic/reply', {
        comicId: comicId ? comicId : -1,
        chapterId: chapterId ? chapterId : -1,
        commentId: cmtId,
        content: content
    }).done(function (response, textStatus, jqXHR) {
        // Xử lý kết quả thành công
        $(`#replies-${cmtId}`).append(handleReply(response, cmtId));
    }).fail(function (jqXHR, textStatus, errorThrown) {
        // Xử lý lỗi
        alert('Lỗi: ' + textStatus + ' - Không thể gửi bình luận rồi Fen ơi!');
    }).always(function () {
        // Thực hiện các công việc cần thiết sau khi request hoàn thành (có thể bao gồm cả thành công và thất bại)
        edt.setData('');
        $('.box-comment-reply').empty();
        isCallApiSendComment = false;
        handleLoaderOnchange();
    });
}
const handleSendComment = () => {
    const content = $('.ck .ck-content');
    if (content.text() === '' || content === '' || content === '<p><br data-cke-filler="true"></p>') {
        alert('Nội dung không được để trống Fen ơi!');
        return;
    }
    if (content.text().length > 1000) {
        alert('Nội dung không được quá 1000 ký tự Fen ơi!');
        return;
    }
    if (!handleCheckLogin()) {
        alert('Phải đăng nhập đã Fen ơi!');
        return;
    }
    handleCallApiSendAddNewComment(comicId, chapterId, content.html());
    handleLoaderOnchange();
}
const handleCallApiSendAddNewComment = (comicId, chapterId, content) => {
    if (isCallApiSendComment) return;
    isCallApiSendComment = true;
    $.post('/api/comments/comic/new', {
        comicId: comicId ? comicId : -1,
        chapterId: chapterId ? chapterId : -1,
        content: content
    }).done(function (response, textStatus, jqXHR) {
        // Xử lý kết quả thành công
        $('#list-comments').prepend(handleComment(response));
    }).fail(function (jqXHR, textStatus, errorThrown) {
        // Xử lý lỗi
        alert('Lỗi: ' + textStatus + ' - Không thể gửi bình luận rồi Fen ơi!');
    }).always(function () {
        // Thực hiện các công việc cần thiết sau khi request hoàn thành (có thể bao gồm cả thành công và thất bại)
        edt.setData('');
        isCallApiSendComment = false;
        handleLoaderOnchange();
    });
}

//----------------------------------------------------------- Call api get comment
const handleAddComment = (comments) => {
    comments.forEach(function (comment) {
        $('#list-comments').append(handleComment(comment));
    });
}
const handleComment = (comment) => {
    return `<div id="comments-${comment.id}" class="shadow my-2 w-full rounded bg-slate-50 dark:bg-slate-700 dark:text-white">
            <div id="comment-${comment.id}">
                <div class="flex p-2 gap-2 w-full">
                    <!-- avatar -->
                    <div class="flex-shrink-0">
                        <a href="/profiles/${comment.userProfileBcId}">
                            <img
                                    src="${comment.userProfileAvatarUrl}"
                                    alt="avatar"
                                    class="w-12 h-12 rounded-full object-cover"
                                    loading="lazy"
                            >
                        </a>
                    </div>
                    <div class="w-full">
                        <div class="md:px-2 md:py-1">
                            <div class="border-b w-full pb-1">
                                <a href="/profiles/${comment.userProfileBcId}">
                                    <h3 class="text-name font-bold">${comment.userProfileFullname}</h3>
                                </a>
                                <div class="flex gap-2 w-full items-center">
                                    ${comment.chapterId ? !window.location.href.match(/\/chapter\/(\d+)/)??[1] ? `<a href="${window.location.href}/chapter/${comment.chapterId}">
                                        <span class="text-xs italic font-light text-gray-400 hover:text-red-500 dark:hover:text-green-500">${comment.chapterTitle}</span>
                                    </a>`: '' : ''}
                                    <span class="text-sm italic font-light text-gray-400">${comment.timeAgo}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- content -->
                <div class="px-2">
                    <div class="bg-white text-sm shadow rounded dark:bg-slate-600 p-2 ck-content">
                        ${comment.content}
                    </div>
                    <div class="flex justify-between items-center">
                            <div class="flex gap-4 md:px-2 py-2 items-center">
                                <button class="btn-reply text-sm font-medium hover:text-red-500 dark:text-white dark:hover:text-green-500"
                                    data-cmt-id="${comment.id}"
                                >
                                    Trả lời
                                </button>
                                <div class="flex gap-2 items-center">
                                    <span class="text-likes text-sm font-medium">${comment.likes > 0 ? comment.likes : ''}</span>
                                    <button class="${handleCheckLikeComment(comment.id) ? 'text-red-500 dark:text-green-500': 'dark:text-white'} btn-likes hover:text-red-500 dark:hover:text-green-500"
                                            data-cmt-id="${comment.id}"
                                    ><i class="fa-solid fa-thumbs-up"></i></button>
                                </div>
                                <div class="flex gap-2 items-center">
                                    <span class="text-dislikes text-sm font-medium">${comment.dislikes > 0 ? comment.dislikes : ''}</span>
                                    <button class="${handleCheckDislikeComment(comment.id) ? 'text-red-500 dark:text-green-500': 'dark:text-white'} btn-dislikes hover:text-red-500 dark:hover:text-green-500"
                                            data-cmt-id="${comment.id}"
                                    ><i class="fa-solid fa-thumbs-down"></i></button>
                                </div>
                                <div class="flex gap-2 items-center">
                                    <button class="${handleCheckReportComment(comment.id) ? 'text-red-500 dark:text-green-500': 'dark:text-white'} btn-reports hover:text-red-500 dark:hover:text-green-500"
                                            data-cmt-id="${comment.id}"
                                    ><i class="fa-solid fa-triangle-exclamation"></i></button>
                                </div>
                                ${handleCheckIsOwnerComment(comment.userProfileId) ? `
                                <div class="flex gap-2 items-center">
                                    <button class="btn-deleted hover:text-red-500 dark:text-white dark:hover:text-green-500"
                                            data-cmt-id="${comment.id}"
                                    ><i class="fa-solid fa-delete-left"></i></button>
                                </div>
                                `: ''}
                            </div>
                            ${comment.totalReplies > 0 ? `
                            <button class="btn-load-reply flex items-center md:px-2 py-2 gap-2 text-sm font-medium hover:text-red-500 dark:text-white dark:hover:text-green-500"
                                    data-cmt-id="${comment.id}"
                                >
                                    <svg id="load-reply" class="hidden animate-spin -ml-1 mr-3 h-5 w-5 text-red-500 dark:text-green-500" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                                        <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                                        <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                                    </svg>
                                    Xem ${comment.totalReplies} trả lời
                                    <i class="fa-solid fa-angle-down"></i>
                            </button>` : ''}
                    </div>
                </div>
            </div>
            <!-- comment reply -->
            <div id="replies-${comment.id}" class="pl-4 md:pl-12 transition duration-300 ease-in-out hidden"></div>
            <!-- comment box reply -->
            <div id="box-comment-${comment.id}" class="box-comment-reply my-4 px-4"></div>
        </div>`;
}
const handleReply = (reply, cmtId) => {
    return `<div id="comment-${reply.id}">
                <div class="flex p-2 gap-2 w-full">
                    <!-- avatar -->
                    <div class="flex-shrink-0">
                        <a href="/profiles/${reply.userProfileBcId}">
                            <img
                                    src="${reply.userProfileAvatarUrl}"
                                    alt="avatar"
                                    class="w-12 h-12 rounded-full object-cover"
                                    loading="lazy"
                            >
                        </a>
                    </div>
                    <div class="w-full">
                        <div class="md:px-2 md:py-1">
                            <div class="border-b w-full pb-1">
                                <a href="/profiles/${reply.userProfileBcId}">
                                    <h3 class="text-name font-bold">${reply.userProfileFullname}</h3>
                                </a>
                                <div class="flex gap-2 w-full items-center">
                                    ${reply.chapterId ? !window.location.href.match(/\/chapter\/(\d+)/) ?? [1] ? `<a href="${window.location.href}/chapter/${reply.chapterId}">
                                        <span class="text-xs italic font-light text-gray-400 hover:text-red-500 dark:hover:text-green-500">${reply.chapterTitle}</span>
                                    </a>` : '' : ''}
                                    <span class="text-sm italic font-light text-gray-400">${reply.timeAgo}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- content -->
                <div class="px-2">
                    <div class="bg-white text-sm shadow rounded dark:bg-slate-600 p-2 ck-content">
                                ${reply.content}
                    </div>
                    <div class="flex gap-4 py-2 md:px-2 items-center">
                            <button class="btn-reply text-sm font-medium hover:text-red-500 dark:text-white dark:hover:text-green-500"
                                    data-cmt-id="${cmtId}"
                            >
                                Trả lời
                            </button>
                            <div class="flex gap-2 items-center">
                                <span class="text-likes text-sm font-medium">${reply.likes}</span>
                                <button class="${handleCheckLikeComment(reply.id) ? 'text-red-500 dark:text-green-500' : 'dark:text-white'} btn-likes hover:text-red-500 dark:hover:text-green-500"
                                        data-cmt-id="${reply.id}"
                                ><i class="fa-solid fa-thumbs-up"></i></button>
                            </div>
                            <div class="flex gap-2 items-center">
                                <span class="text-dislikes text-sm font-medium">${reply.dislikes}</span>
                                <button class="${handleCheckDislikeComment(reply.id) ? 'text-red-500 dark:text-green-500' : 'dark:text-white'} btn-dislikes hover:text-red-500 dark:hover:text-green-500"
                                        data-cmt-id="${reply.id}"
                                ><i class="fa-solid fa-thumbs-down"></i></button>
                            </div>
                            <div class="flex gap-2 items-center">
                                <button class="${handleCheckReportComment(reply.id) ? 'text-red-500 dark:text-green-500' : 'dark:text-white'} btn-reports hover:text-red-500 dark:hover:text-green-500"
                                        data-cmt-id="${reply.id}"
                                ><i class="fa-solid fa-triangle-exclamation"></i></button>
                            </div>
                            ${handleCheckIsOwnerComment(reply.userProfileId) ? `
                            <div class="flex gap-2 items-center">
                                <button class="btn-deleted hover:text-red-500 dark:text-white dark:hover:text-green-500"
                                        data-cmt-id="${reply.id}"
                                ><i class="fa-solid fa-delete-left"></i></button>
                            </div>
                            ` : ''}
                    </div>
                </div>
            </div>`;
}
const handleCallApiGetComment = () => {
    if (isCallApiLoadComment) {
        return;
    }
    isCallApiLoadComment = true;
    $.post('/api/comments/comic/get', {
        comicId: comicId ? comicId : -1,
        chapterId: chapterId ? chapterId : -1,
        page: page,
        size: size
    }).done(function (response, textStatus, jqXHR) {
        // Xử lý kết quả thành công
        if (response.length > 0) {
            handleAddComment(response);
            page++;
        } else {
            $('#list-comments').append(`<div class="font-medium p-4 border-l-2 border-red-500 dark:text-white dark:border-green-500">Không có bình luận nào Fen ơi!</div>`);
            $('#comment-load-more').empty();
        }
    }).fail(function (jqXHR, textStatus, errorThrown) {
        // Xử lý lỗi
        alert('Lỗi: ' + textStatus + ' - Không thể lấy bình luận rồi Fen ơi!');
    }).always(function () {
        // Thực hiện các công việc cần thiết sau khi request hoàn thành (có thể bao gồm cả thành công và thất bại)
        isCallApiLoadComment = false;
        handleLoaderOnchange();
    });
}
const handleCallApiCommentLoadMore = () => {
    handleCallApiGetComment();
    handleLoaderOnchange();
}
const handleLoaderOnchange = () => {
    if (isCallApiLoadComment) {
        $('#comment-load').removeClass('hidden');
    } else {
        $('#comment-load').addClass('hidden');
    }
    if (isCallApiSendComment) {
        $('#send-comment-load').removeClass('hidden');
    } else {
        $('#send-comment-load').addClass('hidden');
    }
}

//----------------------------------------------------------- reply comment
const handleLoadReply = (cmtId) => {
    const replies = $(`#replies-${cmtId}`);
    let option = repliesOption.find(function (item) {
        return item.cmtId === cmtId;
    });
    if (!option) {
        $(`#comment-${cmtId}`).find('#load-reply').removeClass('hidden');
        repliesOption.push({cmtId: cmtId, page: 0, size: 10, isCallApi: false});
        handleCallApiGetReply(cmtId);
    } else {
        replies.toggle('hidden');
    }
}
const handleCallApiGetReply = (cmtId) => {
    let option = repliesOption.find(function (item) {
        return item.cmtId === cmtId;
    });
    $.post('/api/comments/comic/get/replies', {
        commentId: cmtId,
        page: option.page,
        size: option.size
    }).done(function (response, textStatus, jqXHR) {
        // Xử lý kết quả thành công
        const replies = $(`#replies-${cmtId}`);
        response.forEach(function (reply) {
            replies.append(handleReply(reply, cmtId));
            option.page++;
        });
        replies.toggle('hidden');
        $(`#comment-${cmtId}`).find('#load-reply').addClass('hidden');
    }).fail(function (jqXHR, textStatus, errorThrown) {
        // Xử lý lỗi
        alert('Lỗi: ' + textStatus + ' - Không thể lấy trả lời rồi Fen ơi!');
    }).always(function () {
        // Thực hiện các công việc cần thiết sau khi request hoàn thành (có thể bao gồm cả thành công và thất bại)
    });
}

//----------------------------------------------------------- check like, dislike, report, delete comment
const handleCheckLikeComment = (cmtId) => {
    let likes = localStorage.getItem('likes');
    if (likes) {
        likes = JSON.parse(likes);
        if (likes.includes(cmtId)) {
            return true;
        }
    }
    return false;
}
const handleCheckDislikeComment = (cmtId) => {
    let dislikes = localStorage.getItem('dislikes');
    if (dislikes) {
        dislikes = JSON.parse(dislikes);
        if (dislikes.includes(cmtId)) {
            return true;
        }
    }
    return false;
}
const handleCheckReportComment = (cmtId) => {
    let reports = localStorage.getItem('reports');
    if (reports) {
        reports = JSON.parse(reports);
        if (reports.includes(cmtId)) {
            return true;
        }
    }
    return false;
}
const handleCheckIsOwnerComment = (pid) => {
    // check user id in local storage
    let user = localStorage.getItem('virus');
    if (user) {
        user = JSON.parse(user);
        if (user.id === pid) {
            return true;
        }
    }
    return false;
}
const handleCheckLogin = () => {
    // check user id in local storage
    let user = localStorage.getItem('virus');
    return !!user;

}

// Like and dislike comment likes: {"cmtId": 1}, dislikes: {"cmtId": 2}
//----------------------------------------------------------- like comment
const handleLikeComment = (cmtId) => {
    if (!handleCheckLogin()) {
        alert('Phải đăng nhập đã Fen ơi!');
        return;
    }
    // check cmt id in local storage
    let likes = localStorage.getItem('likes');
    if (likes) {
        likes = JSON.parse(likes);
        if (likes.includes(cmtId)) {
            handleCallApiUnlikeComment(cmtId);
        } else {
            // check cmt id in local storage dislikes
            let dislikes = localStorage.getItem('dislikes');
            if (dislikes) {
                dislikes = JSON.parse(dislikes);
                if (dislikes.includes(cmtId)) {
                    alert('Đã không thích bình luận này rồi Fen ơi!');
                } else {
                    handleCallApiLikeComment(cmtId);
                }
            } else {
                handleCallApiLikeComment(cmtId);
            }
        }
    } else {
        handleCallApiLikeComment(cmtId);
    }
}
const handleCallApiLikeComment = (cmtId) => {
    $.post('/api/comments/comic/likes', {
        commentId: cmtId
    }).done(function (response, textStatus, jqXHR) {
        // Xử lý kết quả thành công
        //get cmt id from local storage
        let likes = localStorage.getItem('likes');
        // add cmt id to local storage
        if (likes) {
            likes = JSON.parse(likes);
            likes.push(cmtId);
            localStorage.setItem('likes', JSON.stringify(likes));
        } else {
            localStorage.setItem('likes', JSON.stringify([cmtId]));
        }

        $(`#comment-${cmtId} .btn-likes`).removeClass('dark:text-white').addClass('text-red-500 dark:text-green-500');
        // change text this likes + 1
        $(`#comment-${cmtId} .text-likes`).text($(this).text() + 1);
    }).fail(function (jqXHR, textStatus, errorThrown) {
        // Xử lý lỗi
        alert('Lỗi: ' + textStatus + ' - Không thể thích bình luận rồi Fen ơi!');
    }).always(function () {
        // Thực hiện các công việc cần thiết sau khi request hoàn thành (có thể bao gồm cả thành công và thất bại)
    });
}
const handleCallApiUnlikeComment = (cmtId) => {
    $.post('/api/comments/comic/unlikes', {
        commentId: cmtId
    }).done(function (response, textStatus, jqXHR) {
        // Xử lý kết quả thành công
        //get cmt id from local storage
        let likes = localStorage.getItem('likes');
        // remove cmt id from local storage
        if (likes) {
            likes = JSON.parse(likes);
            likes = likes.filter(function (item) {
                return item !== cmtId;
            });
            localStorage.setItem('likes', JSON.stringify(likes));
        }

        $(`#comment-${cmtId} .btn-likes`).removeClass('text-red-500 dark:text-green-500').addClass('dark:text-white');
        // change text this likes - 1
        $(`#comment-${cmtId} .text-likes`).text($(`#comment-${cmtId} .text-likes`).text() - 1);
    }).fail(function (jqXHR, textStatus, errorThrown) {
        // Xử lý lỗi
        alert('Lỗi: ' + textStatus + ' - Không thể bỏ thích bình luận rồi Fen ơi!');
    }).always(function () {
        // Thực hiện các công việc cần thiết sau khi request hoàn thành (có thể bao gồm cả thành công và thất bại)
    });
}
//----------------------------------------------------------- dislike comment
const handleDislikeComment = (cmtId) => {
    if (!handleCheckLogin()) {
        alert('Phải đăng nhập đã Fen ơi!');
        return;
    }
    // check cmt id in local storage
    let dislikes = localStorage.getItem('dislikes');
    if (dislikes) {
        dislikes = JSON.parse(dislikes);
        if (dislikes.includes(cmtId)) {
            handleCallApiUndislikeComment(cmtId);
        } else {
            // check cmt id in local storage likes
            let likes = localStorage.getItem('likes');
            if (likes) {
                likes = JSON.parse(likes);
                if (likes.includes(cmtId)) {
                    alert('Đã thích bình luận này rồi Fen ơi!');
                } else {
                    handleCallApiDislikeComment(cmtId);
                }
            } else {
                handleCallApiDislikeComment(cmtId);
            }
        }
    } else {
        handleCallApiDislikeComment(cmtId);
    }
}
const handleCallApiDislikeComment = (cmtId) => {
    $.post('/api/comments/comic/dislikes', {
        commentId: cmtId
    }).done(function (response, textStatus, jqXHR) {
        // Xử lý kết quả thành công
        //get cmt id from local storage
        let dislikes = localStorage.getItem('dislikes');
        // add cmt id to local storage
        if (dislikes) {
            dislikes = JSON.parse(dislikes);
            dislikes.push(cmtId);
            localStorage.setItem('dislikes', JSON.stringify(dislikes));
        } else {
            localStorage.setItem('dislikes', JSON.stringify([cmtId]));
        }

        $(`#comment-${cmtId} .btn-dislikes`).removeClass('dark:text-white').addClass('text-red-500 dark:text-green-500');
        $(`#comment-${cmtId} .text-dislikes`).text($(this).text() + 1);
    }).fail(function (jqXHR, textStatus, errorThrown) {
        // Xử lý lỗi
        alert('Lỗi: ' + textStatus + ' - Không thể không thích bình luận rồi Fen ơi!');
    }).always(function () {
        // Thực hiện các công việc cần thiết sau khi request hoàn thành (có thể bao gồm cả thành công và thất bại)
    });
}
const handleCallApiUndislikeComment = (cmtId) => {
    $.post('/api/comments/comic/undislikes', {
        commentId: cmtId
    }).done(function (response, textStatus, jqXHR) {
        // Xử lý kết quả thành công
        //get cmt id from local storage
        let dislikes = localStorage.getItem('dislikes');
        // remove cmt id from local storage
        if (dislikes) {
            dislikes = JSON.parse(dislikes);
            dislikes = dislikes.filter(function (item) {
                return item !== cmtId;
            });
            localStorage.setItem('dislikes', JSON.stringify(dislikes));
        }

        $(`#comment-${cmtId} .btn-dislikes`).removeClass('text-red-500 dark:text-green-500').addClass('dark:text-white');
        $(`#comment-${cmtId} .text-dislikes`).text($(`#comment-${cmtId} .text-dislikes`).text() - 1);
    }).fail(function (jqXHR, textStatus, errorThrown) {
        // Xử lý lỗi
        alert('Lỗi: ' + textStatus + ' - Không thể bỏ không thích bình luận rồi Fen ơi!');
    }).always(function () {
        // Thực hiện các công việc cần thiết sau khi request hoàn thành (có thể bao gồm cả thành công và thất bại)
    });
}

//----------------------------------------------------------- delete comment
const handleDeleteComment = (cmtId) => {
    // alert('Xóa bình luận') after if confirm delete comment
    if (confirm('Bạn có chắc chắn muốn xóa bình luận này không?')) {
        handleCallApiDeletedComment(cmtId);
    }
}
const handleCallApiDeletedComment = (cmtId) => {
    $.post('/api/comments/comic/deleted', {
        commentId: cmtId
    }).done(function (response, textStatus, jqXHR) {
        // Xử lý kết quả thành công
        $(`#comment-${cmtId}`).remove();
        $(`#comments-${cmtId}`).remove();
    }).fail(function (jqXHR, textStatus, errorThrown) {
        // Xử lý lỗi
        alert('Lỗi: ' + textStatus + ' - Không thể xóa bình luận rồi Fen ơi!');
    }).always(function () {
        // Thực hiện các công việc cần thiết sau khi request hoàn thành (có thể bao gồm cả thành công và thất bại)
    });
}
// report comment
const handleReportComment = (cmtId) => {
    if (!handleCheckLogin()) {
        alert('Phải đăng nhập đã Fen ơi!');
        return;
    }
    // check cmt id in local storage
    let reports = localStorage.getItem('reports');
    if (reports) {
        reports = JSON.parse(reports);
        if (reports.includes(cmtId)) {
            alert('Đã báo cáo bình luận này rồi Fen ơi!');
            return;
        }
    }
    // alert('Báo cáo bình luận') after if confirm report comment
    if (confirm('Bạn có chắc chắn muốn báo cáo bình luận này không?')) {
        handleCallApiReportComment(cmtId);
    }
}
const handleCallApiReportComment = (cmtId) => {
    $.post('/api/comments/comic/reports', {
        commentId: cmtId
    }).done(function (response, textStatus, jqXHR) {
        // Xử lý kết quả thành công
        //get cmt id from local storage
        let reports = localStorage.getItem('reports');
        // add cmt id to local storage
        if (reports) {
            reports = JSON.parse(reports);
            reports.push(cmtId);
            localStorage.setItem('reports', JSON.stringify(reports));
        } else {
            localStorage.setItem('reports', JSON.stringify([cmtId]));
        }

        $(`#comment-${cmtId} .btn-reports`).removeClass('dark:text-white').addClass('text-red-500 dark:text-green-500');
    }).fail(function (jqXHR, textStatus, errorThrown) {
        // Xử lý lỗi
        alert('Lỗi: ' + textStatus + ' - Không thể báo cáo bình luận rồi Fen ơi!');
    }).always(function () {
        // Thực hiện các công việc cần thiết sau khi request hoàn thành (có thể bao gồm cả thành công và thất bại)
    });
}

//----------------------------------------------------------- editor and box comment
const handleAddSticker = (editor, src) => {
    const content = `<img src="${src}" alt="sticker">`;
    const viewFragment = editor.data.processor.toView(content);
    const modelFragment = editor.data.toModel(viewFragment);
    editor.model.insertContent(modelFragment, editor.model.document.selection);

    // Hide modal sticker
    $('#modal-sticker').addClass('hidden');
}
const handleAppend = (listSticker) => {
    $('#modal-sticker').removeClass('hidden');
    $('#list-modal-sticker').empty();
    listSticker.forEach(function (item) {
        $('#list-modal-sticker').append('<button class="hover:bg-gray-200 hover:border border-red-500 btn-sticker" type="button">\n' +
            '                <img class="w-full object-cover" src="' + item + '"\n' +
            '                     alt="sticker">\n' +
            '            </button>');
    });
}
const handleAddReply = (cmtId) => {
    $('#comment-parent').removeClass('hidden');
    $('#box-comment').empty();
    $('.box-comment-reply').empty();
    let fullname = $(`#comment-${cmtId} .text-name`).text();
    let strBoxComment = `<h1 class="font-medium border-b border-red-500 mb-2 dark:text-white dark:border-green-500">Comments</h1>
            <div class="relative text-black pb-4">
                <div id="editor">
                    <i><strong>${fullname}</strong></i>
                    <p>Đạo hữu xin dừng bước!</p>
                    <img src="http://3.bp.blogspot.com/-W2rCQRYm1g0/TxaJn8eZW_I/AAAAAAAAFWY/R6YdIWiuobU/Upanhso.tk-11.gif"
                         alt="sticker">
                </div>
                <!-- modal gif -->
                <div id="modal-sticker"
                     class="absolute hidden top-10 left-1/2 -translate-x-1/2 bg-gray-50 shadow w-2/3 md:max-w-3xl h-96 overflow-y-auto">
                    <div id="list-modal-sticker" class="grid grid-cols-4 md:grid-cols-6 gap-2"></div>
                </div>
                <button id="btn-reply-send"
                        data-cmt-id="${cmtId}"
                        type="button"
                        class="inline-flex justify-center mt-2 py-4 px-6 w-full rounded border border-red-500 hover:bg-red-50 dark:bg-slate-800 dark:hover:bg-slate-500 dark:border-green-500 dark:text-white"
                >
                    <svg id="send-comment-load" class="hidden animate-spin -ml-1 mr-3 h-5 w-5 text-red-500 dark:text-green-500" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                        <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                        <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                    </svg>
                    Comment
                </button>
            </div>`;
    $('#box-comment-' + cmtId).append(strBoxComment);
    handleCreateEditor();
}
const handleAddBoxCommentParent = () => {
    $('#comment-parent').addClass('hidden');
    $('.box-comment-reply').empty();
    let strBoxComment = `<h1 class="font-medium border-b border-red-500 mb-2 dark:text-white dark:border-green-500">Comments</h1>
        <div class="relative">
            <div id="editor">
                <p>Đạo hữu xin dừng bước!</p>
                <img src="http://3.bp.blogspot.com/-W2rCQRYm1g0/TxaJn8eZW_I/AAAAAAAAFWY/R6YdIWiuobU/Upanhso.tk-11.gif"
                     alt="sticker">
            </div>
            <!-- modal gif -->
            <div id="modal-sticker"
                 class="absolute hidden top-10 left-1/2 -translate-x-1/2 bg-gray-50 shadow w-2/3 md:max-w-3xl h-96 overflow-y-auto">
                <div id="list-modal-sticker" class="grid grid-cols-4 md:grid-cols-6 gap-2"></div>
            </div>
            <button id="btn-comment-send"
                        type="button"
                        class="mt-2 py-4 px-6 w-full rounded border border-red-500 hover:bg-red-50 dark:bg-slate-800 dark:hover:bg-slate-500 dark:border-green-500 dark:text-white"
                >
                    Comment
                </button>
        </div>`;
    $('#box-comment').append(strBoxComment);
    handleCreateEditor();
}
const handleCreateEditor = () => {
    ClassicEditor.create(document.querySelector('#editor'), {
        toolbar: {
            items: [
                'bold',
                'italic',
                '|',
                'undo',
                'redo',
            ]
        },
        language: 'vi'
    }).then(editor => {
        //editor.model.document.on('change:data', () => {});
        // Insert sticker to editor.
        edt = editor;
        $('.ck-toolbar__items').append(strBtnTrollFace + strBtnOnion + strBtnBafu + strBtnThoBua + strBtnTuzki + strBtnYoyo + strBtnPanda + strBtnBaby + strBtnGif);
    }).catch(error => {
        console.error(error);
    });
}