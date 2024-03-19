//---------------------------------------------
// dark/light mode
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

//---------------------------------------------
$(document).ready(function () {
    handleGetProfileIf();
    // delete virus in local storage after run /logout
    if (window.location.pathname + window.location.search === '/login?logout') {
        localStorage.removeItem('virus');
    }
});

//--------------------------------------------- Functions
const handleGetProfileIf = () => {
    // check if profile is exist
    let virus = JSON.parse(localStorage.getItem('virus'));
    if (virus) return;
    $.post('/api/profiles/if').done(function (response, textStatus, jqXHR) {
        // Xử lý kết quả thành công
        if (response.id) {
            localStorage.setItem('virus', JSON.stringify(response));
        }
    }).fail(function (jqXHR, textStatus, errorThrown) {
        // Xử lý lỗi
        localStorage.removeItem('virus');
    })
}