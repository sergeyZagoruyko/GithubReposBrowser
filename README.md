# Project tech stack:

- **Java**
- **RxJava 3**
- **Dagger 2**
- **Room**
- **ViewModel**
- **MVVM architecture pattern + clean**

## App general features:

- **Trending repositories list:** Receiving general repos list with pagination support when reaching near to the list edge.
- **Search by input text:** Integrated debouncing approach to decrease requests amount while typing. Search and filer icons disappears on the Favorites tab.
- **Filtering by date:** Implemented as popup on filter icon.
- **Detailed info:** Implemented as bottom dialog.
- **Favorites list:** Included support of real-time updates favorites and base lists info based on favorite checkbox change. Used Shared ViewModel approach to satisfy this behavior.

## Possible improvements:

- **Tablets support:** Combining base and detail info on the same screen.
- **Favorite icon on the items' UI:** Currently its available only via opening the details bottom dialog
- **Current filter:** Modal view with filter options might display currently selected filter
- **Favorites local search:** Local search via (DB query / programming filtering) might be introduced.
- **Deeper animations support:** More animations for selection case, favorite adding/removing and etc.
- **Invalid token banner disappearance:** Might be more user-friendly if this banner disappears after some time or auto-retry is happening.
- **Shimmer UI:** Shimmer UI effect for data loading might be more rich solution.
- **Bottom dialog appearance:** Currently contains little jumping effect, that ideally should be handled.
- **Bottom dialog error state:** UI for error state can be implemented similarly to the list screens. Currently it's just showing the popup.