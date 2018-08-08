# RssSampleApp
Небольшое приложение для отслеживания RSS лент разных источников.

<img src="https://github.com/QeGle/RssSampleApp/blob/master/Screenshot_20180808-082206_RSSTestApp.jpg" width="300" /><img src="https://github.com/QeGle/RssSampleApp/blob/master/Screenshot_20180808-082210_RSSTestApp.jpg" width="300" /><img src="https://github.com/QeGle/RssSampleApp/blob/master/Screenshot_20180808-082218_RSSTestApp.jpg" width="300" /><img src="https://github.com/QeGle/RssSampleApp/blob/master/Screenshot_20180808-082246_RSSTestApp.jpg" width="300" />
 
### Features
* Добавление/удаление источников
* Оффлайн доступ к загруженным лентам
* Переход по ссылке для доступа к полной версии новости

### Used technologies
* [OkHttp3](https://square.github.io/okhttp/) - для загрузки rss ленты
* [Room ORM](https://developer.android.com/training/data-storage/room/index.html) - для хранения лент
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata.html) vs RxAndroid(https://github.com/ReactiveX/RxAndroid)- для обновления RecyclerView
