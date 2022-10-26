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
            'GangwonEduAll':['GangwonEduAll', 'pretendard', 'ui-sans-serif', 'system-ui', '-apple-system'
                    , 'BlinkMacSystemFont', 'Roboto', 'Helvetica Neue', 'Arial', 'Noto Sans'
                    , 'sans-serif', 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', 'Noto Color Emoji'
            ],
            'EF_Diary':['EF_Diary', 'pretendard', 'ui-sans-serif', 'system-ui', '-apple-system'
                    , 'BlinkMacSystemFont', 'Roboto', 'Helvetica Neue', 'Arial', 'Noto Sans'
                    , 'sans-serif', 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', 'Noto Color Emoji'
            ],
        },
        colors:{
            'wd-pink' : '#E5809F',
            'wd-mint' : '#59BA97',
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
