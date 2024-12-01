import { Link } from "react-router-dom";
import axios from "axios";
import React, { useState, useEffect } from "react";

const API_URL = "http://localhost:8080/events";
const Posters = () => {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const token = localStorage.getItem("token");

  useEffect(() => {
    const fetchEvents = async () => {
      try {
        setLoading(true);
        const response = await axios.get(`${API_URL}/get-all`, {
          headers: {
            Authorization: `Bearer ${token}`, // Incluye el token en el encabezado
          },
        });
        setEvents(response.data.ourEventsList || []); // Actualiza los datos de eventos
      } catch (err) {
        setError(
          "Error al obtener los eventos. Verifica tu conexión o permisos."
        );
        console.error("Error fetching events:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchEvents();
  }, []);

  useEffect(() => {
    console.log("Eventos cargados:", events);
  }, [events]);

  return (
    <div className="text-center p-10">
      <h1 className="font-bold text-4xl mb-4">
        Tenemos los mejores conciertos para ti
      </h1>
      <h1 className="text-3xl">Artistas de renombre a nivel mundial</h1>

      {loading ? (
        <p className="text-xl mt-10">Cargando conciertos...</p>
      ) : (
        <section
          id="Projects"
          className="w-fit mx-auto grid grid-cols-1 lg:grid-cols-3 md:grid-cols-2 justify-items-center justify-center gap-y-20 gap-x-14 mt-10 mb-5"
        >
          {events.map((event) => (
            <div
              key={event.id} // Cambia según el identificador único en tu API
              className="w-72 bg-white shadow-md rounded-xl duration-500 hover:scale-105 hover:shadow-xl"
            >
              <Link to={`/concert-details/${event.id}`}>
                <img
                  src={event.image}
                  alt={event.artist?.name || "Artista"}
                  className="h-80 w-72 object-cover rounded-t-xl"
                />
                <div className="px-4 py-3 w-72">
                  <p className="text-lg font-bold text-black truncate block capitalize">
                    {event.artist?.name || "Artista Desconocido"}
                  </p>
                  <span className="text-gray-400 mr-3 uppercase text-xs">
                    {event.date || "Fecha no disponible"}
                  </span>
                  <div className="flex items-center">
                    <p className="text-lg font-semibold text-black cursor-auto my-3">
                      {event.place?.name || "Ubicación desconocida"}
                    </p>
                    <div className="ml-auto">
                      <button className="text-blue-500 font-semibold hover:underline">
                        Ver más
                      </button>
                    </div>
                  </div>
                </div>
              </Link>
            </div>
          ))}
        </section>
      )}
    </div>
  );
};

export default Posters;
