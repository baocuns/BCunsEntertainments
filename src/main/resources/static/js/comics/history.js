// 1. comic history
// 2. chapter history
// 3. count chapter: thong bao khi co chapter moi

$(document).ready(function () {
    handleGetComicHistoryLocalStorage();
});

const handleGetComicHistoryLocalStorage = () => {
    // get virus
    let virus = localStorage.getItem("virus");
    if (virus != null) {
        virus = JSON.parse(virus);
    }
    // 1. comic history
    let comicHistory = localStorage.getItem("comicHistory");
    if (comicHistory != null) {
        comicHistory = JSON.parse(comicHistory);
        comicHistory.forEach(function (item) {
            if (virus != null && virus.bcId === item.userId) {
                handleGenerateComicHistory(item.comics);
            } else if (item.userId === null) {
                handleGenerateComicHistory(item.comics);
            }
        });
    }
}

const handleGenerateComicHistory = (comics) => {
    comics.forEach(function (comic) {
        $("#history-comics").append(`<div class="shadow rounded">
                    <a href="/comic/${comic.id}"
                       class="flex gap-2">
                        <img class="flex-none w-24 sm:w-40 h-32 sm:h-48 object-cover rounded"
                             src="${comic.thumbnail}"
                             alt="${comic.title}"/>
                        <div>
                            <p class="sm:text-xl font-bold dark:text-white">${comic.title}</p>
                            <p class="text-sm sm:text-base dark:text-gray-400">Chapter ${comic.countChapter}</p>
                            <p class="text-sm sm:text-base dark:text-gray-400">Cập nhập ${comic.timeUpdate}</p>
                            <p class="text-sm sm:text-base dark:text-gray-400">Đọc tiếp chapter ${comic.chapterRead}</p>
                            <p class="text-sm sm:text-base dark:text-gray-400">Đã đọc ${dateToTimeAgo(comic.timeRead)}</p>
                        </div>
                    </a>
                </div>`);
    });
}

// TODO: 6/8/2024 - Sửa lại cách lưu history và lấy history