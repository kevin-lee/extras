const algoliaConfig = require('./algolia.config.json');
const googleAnalyticsConfig = require('./google-analytics.config.json');

const lightCodeTheme = require('prism-react-renderer/themes/nightOwlLight');
const darkCodeTheme = require('prism-react-renderer/themes/nightOwl');

const isEmptyObject = obj => {
  for (field in obj) return false;
  return true;
};

const isSearchable = !isEmptyObject(algoliaConfig);
const hasGoogleAnalytics = !isEmptyObject(googleAnalyticsConfig);

// With JSDoc @type annotations, IDEs can provide config autocompletion
/** @type {import('@docusaurus/types').DocusaurusConfig} */
const websiteConfig = {
  title: 'Extras',
  tagline: 'A few little extra tools',
  url: 'https://extras.kevinly.dev',
  baseUrl: '/',
  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'warn',
  favicon: 'img/favicon.png',
  organizationName: 'Kevin-Lee', // Usually your GitHub org/user name.
  projectName: 'extras', // Usually your repo name.

  presets: [
    [
      '@docusaurus/preset-classic',
      /** @type {import('@docusaurus/preset-classic').Options} */
      ({
        docs: {
          path: '../generated-docs/target/mdoc/',
          sidebarPath: require.resolve('./sidebars.js'),
        },
        theme: {
          customCss: require.resolve('./src/css/custom.css'),
        },
      }),
    ],
  ],

  themeConfig:
    /** @type {import('@docusaurus/preset-classic').ThemeConfig} */
    ({
      navbar: {
        title: 'Extras',
        logo: {
          alt: 'Extras Logo',
          src: 'img/extras-logo-32x32.png',
        },
        items: [
          {
            type: 'doc',
            docId: 'intro',
            activeBasePath: 'docs',
            position: 'left',
            label: 'Docs',
          },
          {
            href: 'https://github.com/Kevin-Lee/extras',
            label: 'GitHub',
            position: 'right',
          },
        ],
      },
      footer: {
        style: 'dark',
        links: [
          {
            title: 'Docs',
            items: [
              {
                label: 'Docs',
                to: '/docs/',
              },
            ],
          },
          {
            title: 'More',
            items: [
              {
                label: 'GitHub',
                href: 'https://github.com/Kevin-Lee/extras',
              },
            ],
          },
        ],
        copyright: `Copyright © ${new Date().getFullYear()} Extras written by <a href="https://github.com/Kevin-Lee" target="_blank"><b>Kevin Lee</b></a>, The website built with Docusaurus.
        <div>Icons made by <a href="https://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
        `,
      },
      prism: {
        theme: lightCodeTheme,
        darkTheme: darkCodeTheme,
        additionalLanguages: [
          'java',
          'scala',
        ],
      },
    }),
  plugins: [
    require.resolve('docusaurus-lunr-search'),
  ],
};

if (isSearchable) {
  websiteConfig['themeConfig']['algolia'] = algoliaConfig;
}
if (hasGoogleAnalytics) {
  websiteConfig['themeConfig']['googleAnalytics'] = googleAnalyticsConfig;
}

module.exports = websiteConfig;
