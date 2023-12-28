

Navigation with Compose doesn't allow you to navigate within a graph by default

#### Unexpected Navigation:

https://github.com/Chozzle/sample-compose-tab-nav/assets/11524191/57b75f50-fd82-4c40-a24c-1b606060c203

Please check [these changes](https://github.com/Chozzle/sample-compose-tab-nav/pull/1/files) for the fix

This design seems quite difficult to maintain, as all destinations nested anywhere within the graph need to now track
from which parent they were navigated to.

#### Fixed Navigation:

https://github.com/Chozzle/sample-compose-tab-nav/assets/11524191/94e35cc3-6247-43c5-a0fd-9c9f6c1924b7
