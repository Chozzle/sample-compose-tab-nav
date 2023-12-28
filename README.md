

Navigation with Compose doesn't allow you to navigate within a graph by default

![Fixed](media/unexpectednav.webm)


Please check [these changes](https://github.com/Chozzle/sample-compose-tab-nav/pull/1/files) for the fix

This design seems quite difficult to maintain, as all destinations nested anywhere within the graph need to now track
from which parent they were navigated to.

![Fixed](media/fixednav.webm)