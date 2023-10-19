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