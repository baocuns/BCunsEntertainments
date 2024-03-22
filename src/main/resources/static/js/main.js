// --------------------------------------------- ALL FUNCTION ---------------------------------------------
const diacriticsMap = {
    'á': 'a', 'à': 'a', 'ả': 'a', 'ã': 'a', 'ạ': 'a',
    'ă': 'a', 'ắ': 'a', 'ằ': 'a', 'ẳ': 'a', 'ẵ': 'a', 'ặ': 'a',
    'â': 'a', 'ấ': 'a', 'ầ': 'a', 'ẩ': 'a', 'ẫ': 'a', 'ậ': 'a',
    'đ': 'd',
    'é': 'e', 'è': 'e', 'ẻ': 'e', 'ẽ': 'e', 'ẹ': 'e',
    'ê': 'e', 'ế': 'e', 'ề': 'e', 'ể': 'e', 'ễ': 'e', 'ệ': 'e',
    'í': 'i', 'ì': 'i', 'ỉ': 'i', 'ĩ': 'i', 'ị': 'i',
    'ó': 'o', 'ò': 'o', 'ỏ': 'o', 'õ': 'o', 'ọ': 'o',
    'ô': 'o', 'ố': 'o', 'ồ': 'o', 'ổ': 'o', 'ỗ': 'o', 'ộ': 'o',
    'ơ': 'o', 'ớ': 'o', 'ờ': 'o', 'ở': 'o', 'ỡ': 'o', 'ợ': 'o',
    'ú': 'u', 'ù': 'u', 'ủ': 'u', 'ũ': 'u', 'ụ': 'u',
    'ư': 'u', 'ứ': 'u', 'ừ': 'u', 'ử': 'u', 'ữ': 'u', 'ự': 'u',
    'ý': 'y', 'ỳ': 'y', 'ỷ': 'y', 'ỹ': 'y', 'ỵ': 'y',
    'Á': 'A', 'À': 'A', 'Ả': 'A', 'Ã': 'A', 'Ạ': 'A',
    'Ă': 'A', 'Ắ': 'A', 'Ằ': 'A', 'Ẳ': 'A', 'Ẵ': 'A', 'Ặ': 'A',
    'Â': 'A', 'Ấ': 'A', 'Ầ': 'A', 'Ẩ': 'A', 'Ẫ': 'A', 'Ậ': 'A',
    'Đ': 'D',
    'É': 'E', 'È': 'E', 'Ẻ': 'E', 'Ẽ': 'E', 'Ẹ': 'E',
    'Ê': 'E', 'Ế': 'E', 'Ề': 'E', 'Ể': 'E', 'Ễ': 'E', 'Ệ': 'E',
    'Í': 'I', 'Ì': 'I', 'Ỉ': 'I', 'Ĩ': 'I', 'Ị': 'I',
    'Ó': 'O', 'Ò': 'O', 'Ỏ': 'O', 'Õ': 'O', 'Ọ': 'O',
    'Ô': 'O', 'Ố': 'O', 'Ồ': 'O', 'Ổ': 'O', 'Ỗ': 'O', 'Ộ': 'O',
    'Ơ': 'O', 'Ớ': 'O', 'Ờ': 'O', 'Ở': 'O', 'Ỡ': 'O', 'Ợ': 'O',
    'Ú': 'U', 'Ù': 'U', 'Ủ': 'U', 'Ũ': 'U', 'Ụ': 'U',
    'Ư': 'U', 'Ứ': 'U', 'Ừ': 'U', 'Ử': 'U', 'Ữ': 'U', 'Ự': 'U',
    'Ý': 'Y', 'Ỳ': 'Y', 'Ỷ': 'Y', 'Ỹ': 'Y', 'Ỵ': 'Y',
};
// text to slug
const handleTextToSlug = (text) => {
    return text.toLowerCase().replace(/./g, function () {
        return diacriticsMap[arguments[0]] || arguments[0];
    }).trim().replace(/\s+/g, '-').replace(/-+/g, '-').replace(/[^\w-]+/g, '');
}

//--------------------------------------------- Document Ready Function ---------------------------------------------
$(document).ready(function () {
    // field
    // events
    // delete virus in local storage after run /logout
    if (window.location.pathname + window.location.search === '/login?logout') {
        localStorage.removeItem('virus');
    }
    // btn on open menu
    $(document).on('click', '#btn-account', function () {
        $('#account-menu').toggleClass('hidden');
    });
    $(document).on('click', '#btn-account-mobile', function () {
        $('#account-menu-mobile').toggleClass('hidden');
    });
    // btn on close menu when click outside
    $(document).on('click', function (e) {
        if (!$(e.target).closest('#btn-account').length) {
            $('#account-menu').addClass('hidden');
        }
        if (!$(e.target).closest('#btn-account-mobile').length) {
            $('#account-menu-mobile').addClass('hidden');
        }
    });
    // function
    handleScrollHomePage();
    handleDarkLightMode();
    handleGetProfileIf();
    handleAddAccountMenu();
});

//--------------------------------------------- Functions
// scroll header
const handleScrollHomePage = () => {
    let isScrollDown = false;
    if (isHomePage()) {
        $('.animation_scroll').removeClass('bg-white/90 shadow text-black dark:bg-slate-800/90')
            .addClass('bg-gradient-to-b from-black/50 to-transparent text-white');
    } else {
        $('.animation_scroll').removeClass('bg-gradient-to-b from-black/50 to-transparent text-white')
            .addClass('bg-white/90 shadow text-black dark:bg-slate-800/90');
    }
    let lastScrollTop = 0;
    $(window).scroll(function () {
        let currentScrollTop = $(this).scrollTop();
        isScrollDown = currentScrollTop > lastScrollTop;
        lastScrollTop = currentScrollTop;

        updateScrollClass();

        // set background header
        if (isHomePage()) {
            if (currentScrollTop > 100) {
                $('.animation_scroll').removeClass('bg-gradient-to-b from-black/50 to-transparent text-white')
                    .addClass('bg-white/90 text-black dark:bg-slate-800/90');
            } else {
                $('.animation_scroll').removeClass('bg-white/90 shadow text-black dark:bg-slate-800/90')
                    .addClass('bg-gradient-to-b from-black/50 to-transparent text-white');
            }
        }
    });

    function updateScrollClass() {
        var $div = $('.animation_scroll');

        if (isScrollDown) {
            $div.addClass('is_scroll');
            $div.removeClass('shadow');
        } else {
            $div.removeClass('is_scroll');
            $div.addClass('shadow');
        }
    }
    function isHomePage() {
        // Get the current URL
        var currentURL = window.location.href;

        // Check if the current URL ends with a "/"
        return currentURL.endsWith('/');
    }
}
// dark/light mode
const handleDarkLightMode = () => {
    const toggleSwitchs = document.querySelectorAll('#theme-switch');
    const toggleIcons = document.querySelectorAll('#theme-icon');

// On page load or when changing themes, best to add inline in `head` to avoid FOUC
    if (localStorage.theme === 'dark' || (!('theme' in localStorage) && window.matchMedia('(prefers-color-scheme: dark)').matches)) {
        document.documentElement.classList.add('dark')
        toggleIcons.forEach(element => {
            element.classList.add('fa-moon-stars');
            element.classList.remove('fa-sun-bright');
        });
    } else {
        document.documentElement.classList.remove('dark')
        toggleIcons.forEach(element => {
            element.classList.add('fa-sun-bright');
            element.classList.remove('fa-moon-stars');
        });
    }

    toggleSwitchs.forEach(element => {
        element.addEventListener('click', function () {
            let theme = 'light';
            if (document.documentElement.classList.contains('dark')) {
                document.documentElement.classList.remove('dark');
                toggleIcons.forEach(element => {
                    element.classList.add('fa-sun-bright');
                    element.classList.remove('fa-moon-stars');
                });
                theme = 'light';
            } else {
                document.documentElement.classList.add('dark');
                toggleIcons.forEach(element => {
                    element.classList.add('fa-moon-stars');
                    element.classList.remove('fa-sun-bright');
                });
                theme = 'dark';
            }
            localStorage.setItem('theme', theme);
        });
    });
}
// get profile if exist
const handleGetProfileIf = () => {
    // check if profile is exist
    let virus = JSON.parse(localStorage.getItem('virus'));
    if (virus) return;
    $.post('/api/profiles/if').done(function (response, textStatus, jqXHR) {
        // Xử lý kết quả thành công
        if (response.id) {
            localStorage.setItem('virus', JSON.stringify(response));
            handleAddAccountMenu();
        }
    }).fail(function (jqXHR, textStatus, errorThrown) {
        // Xử lý lỗi
        localStorage.removeItem('virus');
    })
}
// add account menu
const handleAddAccountMenu = () => {
    let virus = JSON.parse(localStorage.getItem('virus'));
    let accountMenu = $('#account-menu');
    let accountMenuMobile = $('#account-menu-mobile');
    let html = '';

    accountMenu.empty();
    if (!virus) {
        html = `
            <a href="/login" 
            class="block px-4 py-2 text-sm text-gray-700 dark:text-white hover:bg-gray-100 hover:text-red-500 dark:hover:bg-slate-700 dark:hover:text-green-500">Đăng Nhập</a>
            <a href="/register" 
            class="block px-4 py-2 text-sm text-gray-700 dark:text-white hover:bg-gray-100 hover:text-red-500 dark:hover:bg-slate-700 dark:hover:text-green-500">Đăng Ký</a>
        `;
        accountMenu.append(html);
        accountMenuMobile.append(html);
        return;
    }
    html = `
        <a href="/profiles/${virus.bcId}" 
        class="block px-4 py-2 text-sm text-gray-700 dark:text-white hover:bg-gray-100 hover:text-red-500 dark:hover:bg-slate-700 dark:hover:text-green-500">
            <span class="text-sm">Hi </span>
            <span class="text-sm italic font-bold">${virus.fullname}</span>
        </a>
        <a href="/logout" 
        class="block px-4 py-2 text-sm text-gray-700 dark:text-white hover:bg-gray-100 hover:text-red-500 dark:hover:bg-slate-700 dark:hover:text-green-500">Logout</a>
    `;
    accountMenu.append(html);
    accountMenuMobile.append(html);
}