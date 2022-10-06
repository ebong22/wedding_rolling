/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/main/resources/**/**/*.{html,js}"],
  theme: {
    extend: {
        fontFamily:{
            'pretendard':['pretendard', 'ui-sans-serif', 'system-ui', '-apple-system'
                    , 'BlinkMacSystemFont', 'Roboto', 'Helvetica Neue', 'Arial', 'Noto Sans'
                    , 'sans-serif', 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', 'Noto Color Emoji'
            ],
        },
        colors:{
            'wd-pink' : '#E5809F',
        },
    },
    sreens: {
        'sm': '640px',
        'md': '1024px',
        'lg': '1280px',
    },
  },
  plugins: [],
}
