/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {},
    fontFamily: {
      'sans' : ['Roboto','Helvetica']
    },

    screens: {
      'm-s' : '320px',
      'sm': '640px',
      // => @media (min-width: 640px) { ... }

      'md': '768px',
      // => @media (min-width: 768px) { ... }

      'lg': '1024px',
      // => @media (min-width: 1024px) { ... }
      'lg-s' : '1038px',

      'xl': '1280px',
      // => @media (min-width: 1280px) { ... }


      '2xl': '1440px',
      // => @media (min-width: 1536px) { ... }
      // 'tall': { 'raw': '(min-height: 800px)' },
      '3xl': '1800px',

      '4xl' : '2560px'
      
    }
  },
  
  plugins: [require('@tailwindcss/forms')],
}