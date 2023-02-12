## Gatitos
App basada en la API REST thecatapi.com

Este texto describe una aplicación móvil que se basa en la API REST https://docs.thecatapi.com/. La aplicación utiliza el patrón de presentación MVVM y conecta con la API a través de Retrofit para mostrar los resultados obtenidos en un RecyclerView.

La aplicación está compuesta por una sola actividad que es responsable de cargar diferentes fragmentos que muestran las diferentes secciones de la aplicación. Al pulsar en un gato, se lleva a una sección de detalles donde se puede ver información sobre el gato y votar si es un favorito o si gusta.

El menú lateral da acceso a la sección de favoritos, donde se puede ver una lista de favoritos y eliminarlos si se desea. La sección de "Me gusta" es técnicamente igual, pero con un diseño diferente.

El menú superior incluye un buscador que filtra la información en cada pulsación. La aplicación también incluye elementos técnicos como TabLayout, Pull To Refresh en la lista, buscador en RecyclerView, ProgressBar, menú lateral a través de fragmentos, Retrofit y Gson. La información se transfiere entre actividades mediante la clase Parcelable.
