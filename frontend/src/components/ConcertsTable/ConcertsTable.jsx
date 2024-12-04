import axios from "axios";
import React, { useEffect, useState } from "react";

const API_URL = "http://localhost:8080/events";
const API_URL2 = "http://localhost:8080/artists";
const API_URL3 = "http://localhost:8080/places";

const EventsTable = () => {
  const [events, setEvents] = useState([]);
  const [artists, setArtists] = useState([]);
  const [places, setplaces] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [modalData, setModalData] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [selectedEvent, setSelectedEvent] = useState(null);

  const token = localStorage.getItem("token");
  useEffect(() => {
    fetchEvents();
    fetchArtists();
    fetchPlaces();
  }, []);

  useEffect(() => {
    console.log("Eventos cargados:", events);
  }, [events]);

  useEffect(() => {
    console.log("Artistas cargados:", artists);
  }, [artists]);

  useEffect(() => {
    console.log("Lugares cargados:", places);
  }, [artists]);

  const fetchEvents = async () => {
    try {
      setLoading(true);
      const response = await axios.get(`${API_URL}/get-all`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setEvents(response.data.ourEventsList || []); 
    } catch (err) {
      setError(
        "Error al obtener los eventos. Verifica tu conexión o permisos."
      );
      console.error("Error fetching events:", err);
    } finally {
      setLoading(false);
    }
  };

  const fetchArtists = async () => {
    try {
      const response = await axios.get(`${API_URL2}/get-all`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      console.log("Datos de artistas:", response.data); 
      setArtists(response.data.ourArtistsList || []); 
    } catch (err) {
      console.error("Error al obtener los artistas:", err);
      setError(
        "Error al obtener la lista de artistas. Verifica tu conexión o permisos."
      );
    }
  };

  const fetchPlaces = async () => {
    try {
      const response = await axios.get(`${API_URL3}/get-all`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      console.log("Datos de lugares:", response.data);
      setplaces(response.data.ourPlacesList || []); 
    } catch (err) {
      console.error("Error al obtener los lugares:", err);
      setError(
        "Error al obtener la lista de lugares. Verifica tu conexión o permisos."
      );
    }
  };

  const handleCreate = () => {
    setIsEditing(false);
    setModalData({
      name: "",
      date: "",
      hour: "",
      description: "",
      musicalGenre: "",
      status: "",
      image: "",
      place: "",
      artist: "",
    });
    setShowModal(true);
  };

  const handleEdit = (event) => {
    setIsEditing(true);
    setModalData(event);
    setShowModal(true);
  };

  const handleDelete = async () => {
    try {
      await axios.delete(`${API_URL}/delete/${selectedEvent.id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setEvents(events.filter((event) => event.id !== selectedEvent.id));
      setIsDeleteModalOpen(false);
    } catch (err) {
      setError("Error al eliminar el evento.");
    }
  };

  const openDeleteModal = (event) => {
    setSelectedEvent(event);
    setIsDeleteModalOpen(true);
  };

  const validateData = () => {
    const {
      name,
      date,
      hour,
      description,
      musicalGenre,
      status,
      image,
      place,
      artist,
    } = modalData;
    if (
      !name ||
      !date ||
      !hour ||
      !description ||
      !musicalGenre ||
      !status ||
      !image ||
      !place?.id ||
      !artist?.id
    ) {
      setError("Todos los campos son obligatorios.");
      return false;
    }
    return true;
  };
  const handleSave = async () => {
    if (!validateData()) {
      return;
    }
    try {
      if (isEditing) {
        await axios.put(`${API_URL}/update/${modalData.id}`, modalData, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
      } else {
        await axios.post(`${API_URL}/add`, modalData, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
      }
      setShowModal(false);
      fetchEvents();
    } catch (err) {
      setError("Error al guardar el evento.");
      console.error("Error saving event:", err);
    }
  };

  
  const handleCancel = () => {
    setShowModal(false);
    setModalData(null);
  };

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Eventos</h1>

      {/* Mensaje de carga */}
      {loading && <p>Cargando eventos...</p>}

      {/* Mensaje de error */}
      {error && <p className="text-red-500">{error}</p>}

      {/* Tabla de eventos */}
      {!loading && !error && (
        <div>
          <button
            onClick={handleCreate}
            className="mb-4 p-2 bg-blue-500 text-white rounded"
          >
            Crear Evento
          </button>
          <table className="table-auto w-full border-collapse border border-gray-300">
            <thead>
              <tr className="bg-gray-200">
                <th className="border border-gray-300 px-4 py-2">Nombre</th>
                <th className="border border-gray-300 px-4 py-2">Fecha</th>
                <th className="border border-gray-300 px-4 py-2">Hora</th>
                <th className="border border-gray-300 px-4 py-2">
                  Descripción
                </th>
                <th className="border border-gray-300 px-4 py-2">Estado</th>
                <th className="border border-gray-300 px-4 py-2">Lugar</th>
                <th className="border border-gray-300 px-4 py-2">Artista</th>
                <th className="border border-gray-300 px-4 py-2">Acciones</th>
              </tr>
            </thead>
            <tbody>
              {events.map((event) => (
                <tr key={event.id} className="hover:bg-gray-100">
                  <td className="border border-gray-300 px-4 py-2">
                    {event.name}
                  </td>
                  <td className="border border-gray-300 px-4 py-2">
                    {event.date}
                  </td>
                  <td className="border border-gray-300 px-4 py-2">
                    {event.hour}
                  </td>
                  <td className="border border-gray-300 px-4 py-2">
                    {event.description}
                  </td>
                  <td className="border border-gray-300 px-4 py-2">
                    {event.status}
                  </td>
                  <td className="border border-gray-300 px-4 py-2">
                    <div>
                      <p>
                        <strong>Nombre:</strong> {event.place.name}
                      </p>
                      <p>
                        <strong>Ciudad:</strong> {event.place.city}
                      </p>
                    </div>
                  </td>
                  <td className="border border-gray-300 px-4 py-2">
                    <div>
                      <p>
                        <strong>Nombre:</strong> {event.artist.name}
                      </p>
                      <p>
                        <strong>Género:</strong> {event.artist.musicalGenre}
                      </p>
                    </div>
                  </td>
                  <td className="border border-gray-300 px-4 py-2">
                    <button
                      onClick={() => handleEdit(event)}
                      className="px-4 py-2 bg-yellow-500 text-white rounded mr-2"
                    >
                      Editar
                    </button>
                    <button
                      onClick={() => openDeleteModal(event)}
                      className="ml-2 px-4 py-2 font-medium text-white bg-red-600 rounded-md hover:bg-red-500 focus:outline-none"
                    >
                      Eliminar
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {/* Modal de confirmación de eliminación */}
      {isDeleteModalOpen && selectedEvent && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
          <div className="bg-white rounded-lg shadow p-6 w-1/3 text-center">
            <h2 className="text-lg font-bold mb-4">Confirmar Eliminación</h2>
            <p className="mb-4">
              ¿Estás seguro de que quieres eliminar <b>{selectedEvent.name}</b>?
            </p>
            <div className="flex justify-center">
              <button
                onClick={() => setIsDeleteModalOpen(false)}
                className="px-4 py-2 bg-gray-200 rounded mr-2"
              >
                Cancelar
              </button>
              <button
                onClick={handleDelete}
                className="px-4 py-2 bg-red-600 text-white rounded"
              >
                Eliminar
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Modal de creación/edición de eventos */}
      {showModal && (
        <div className="fixed inset-0 z-50 flex justify-center items-center bg-black bg-opacity-50">
          <div className="bg-white p-6 rounded-lg shadow-lg w-1/3 max-h-screen overflow-y-auto">
            <h2 className="text-lg font-bold mb-4">
              {isEditing ? "Editar Evento" : "Crear Evento"}
            </h2>
            <form>
              <div className="mb-4">
                <label className="block text-sm font-semibold">
                  Nombre del Evento
                </label>
                <input
                  type="text"
                  value={modalData.name}
                  onChange={(e) =>
                    setModalData({ ...modalData, name: e.target.value })
                  }
                  className="w-full p-2 border border-gray-300 rounded"
                  placeholder="Nombre"
                />
              </div>
              <div className="mb-4">
                <label className="block text-sm font-semibold">Fecha</label>
                <input
                  type="date"
                  value={modalData.date}
                  onChange={(e) =>
                    setModalData({ ...modalData, date: e.target.value })
                  }
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>
              <div className="mb-4">
                <label className="block text-sm font-semibold">Hora</label>
                <input
                  type="time"
                  value={modalData.hour}
                  onChange={(e) =>
                    setModalData({ ...modalData, hour: e.target.value })
                  }
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>
              <div className="mb-4">
                <label className="block text-sm font-semibold">
                  Descripción
                </label>
                <textarea
                  value={modalData.description}
                  onChange={(e) =>
                    setModalData({ ...modalData, description: e.target.value })
                  }
                  className="w-full p-2 border border-gray-300 rounded"
                  placeholder="Descripción"
                />
              </div>
              <div className="mb-4">
                <label className="block text-sm font-semibold">
                  Genero musical
                </label>
                <textarea
                  value={modalData.musicalGenre}
                  onChange={(e) =>
                    setModalData({ ...modalData, musicalGenre: e.target.value })
                  }
                  className="w-full p-2 border border-gray-300 rounded"
                  placeholder="Genero musical"
                />
              </div>
              <div className="mb-4">
                <label className="block text-sm font-semibold"> Status</label>
                <textarea
                  value={modalData.status}
                  onChange={(e) =>
                    setModalData({ ...modalData, status: e.target.value })
                  }
                  className="w-full p-2 border border-gray-300 rounded"
                  placeholder="Status"
                />
              </div>
              <div className="mb-4">
                <label className="block text-sm font-semibold"> Imagen</label>
                <textarea
                  value={modalData.image}
                  onChange={(e) =>
                    setModalData({ ...modalData, image: e.target.value })
                  }
                  className="w-full p-2 border border-gray-300 rounded"
                  placeholder="Link de la imagen"
                />
              </div>

              <div className="mb-4">
                <label className="block text-sm font-semibold">Lugar</label>
                <select
                  value={modalData.place?.id || ""}
                  onChange={(e) =>
                    setModalData({
                      ...modalData,
                      place: { id: e.target.value },
                    })
                  }
                  className="w-full p-2 border border-gray-300 rounded"
                >
                  <option value="" disabled>
                    Selecciona el lugar
                  </option>
                  {places.map((place) => (
                    <option key={place.id} value={place.id}>
                      {place.name}
                    </option>
                  ))}
                </select>
              </div>

              <div className="mb-4">
                <label className="block text-sm font-semibold">Artista</label>
                <select
                  value={modalData.artist?.id || ""}
                  onChange={(e) =>
                    setModalData({
                      ...modalData,
                      artist: { id: e.target.value },
                    })
                  }
                  className="w-full p-2 border border-gray-300 rounded"
                >
                  <option value="" disabled>
                    Selecciona un artista
                  </option>
                  {artists.map((artist) => (
                    <option key={artist.id} value={artist.id}>
                      {artist.name}
                    </option>
                  ))}
                </select>
              </div>

              <div className="flex justify-end">
                <button
                  type="button"
                  onClick={handleCancel}
                  className="px-4 py-2 bg-gray-200 rounded mr-2"
                >
                  Cancelar
                </button>
                <button
                  type="button"
                  onClick={handleSave}
                  className="px-4 py-2 bg-blue-500 text-white rounded"
                >
                  Guardar
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};
export default EventsTable;
