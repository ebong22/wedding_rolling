/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/main/resources/**/**/*.{html,js}"],
  theme: {
    extend: {},
    screens: {
        'sm': '640px',
        'md': '1024px',
        'lg': '1280px',
    },
  },
  plugins: [],
}
