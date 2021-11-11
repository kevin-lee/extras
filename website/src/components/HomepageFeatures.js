import React from 'react';
import clsx from 'clsx';
import styles from './HomepageFeatures.module.css';

const FeatureList = [
  {
    title: 'A few tools',
    Png: require('../../static/img/tool-box.png').default,
    description: (
      <>
        'Extras' provides a few tools.
      </>
    ),
  },
  {
    title: 'Extra functions',
    Png: require('../../static/img/extra.png').default,
    description: (
      <>
        It provides extra functions to a few existing libraries.
      </>
    ),
  },
  {
    title: 'Scala 2 and 3',
    Svg: require('../../static/img/scala.svg').default,
    description: (
      <>
        It supports both Scala 2 and Scala 3.
      </>
    ),
  },
];

function FeatureSvg({Svg, title, description}) {
  return (
    <div className={clsx('col col--4')}>
      <div className="text--center">
        <Svg className={styles.featureSvg} alt={title} />
      </div>
      <div className="text--center padding-horiz--md">
        <h3>{title}</h3>
        <p>{description}</p>
      </div>
    </div>
  );
}

function FeaturePng({Png, title, description}) {
  return (
    <div className={clsx('col col--4')}>
      <div className="text--center">
        <img src={Png} className={styles.featurePng} alt={title} />
      </div>
      <div className="text--center padding-horiz--md">
        <h3>{title}</h3>
        <p>{description}</p>
      </div>
    </div>
  );
}

export default function HomepageFeatures() {
  return (
    <section className={styles.features}>
      <div className="container">
        <div className="row">
          {FeatureList.map((props, idx) => (
            props.hasOwnProperty('Svg') ?
              <FeatureSvg key={idx} {...props} /> :
              <FeaturePng key={idx} {...props} />
          ))}
        </div>
      </div>
    </section>
  );
}
