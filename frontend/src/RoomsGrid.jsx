import { useState, useEffect } from 'react';
import axios from 'axios';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';

export function RoomsGrid() {
    const [loading, setLoading] = useState(true);
    const [rooms, setRooms] = useState([]);
    const [deleteModal, setDeleteModal] = useState(false);
    const [dataModal, setDataModal] = useState(false);
    const [selectedItem, setSelectedItem] = useState();

    useEffect(() => {
        fetchAllRooms();
    }, []);

    async function fetchAllRooms() {
        try {
            const response = await axios.get('/api/apartamento');

            setRooms(response.data);

            setTimeout(() => setLoading(false), 1000);
        } catch(error) {
            alert('Ocorreu algum erro ao pegar dados.');
            console.log(error);
        }
    }

    async function deleteItem() {
        setLoading(true);

        try {
            await axios.delete(`/api/apartamento/${selectedItem.id}`);

            fetchAllRooms();

            closeDeleteModal();
        } catch(error) {
            alert('Ocorreu algum erro ao deletar dados.');
            console.log(error);
        }
    }

    async function createOrUpdate() {
        if (isEditMode()) {
            try {
                await axios.put(`/api/apartamento/${selectedItem.id}`, selectedItem);

                fetchAllRooms();

                closeDataModal();
            } catch(error) {
                alert('Ocorreu algum erro ao editar dados: ' + error.response.data.message);
                console.log(error);
            }

            return;
        }

        try {
            await axios.post('/api/apartamento', selectedItem);

            fetchAllRooms();

            closeDataModal();
        } catch(error) {
            alert('Ocorreu algum erro ao criar: ' + error.response.data.message);
            console.log(error);
        }
    }

    function showDeleteModal(item) {
        setSelectedItem(item);
        setDeleteModal(true);
    }

    function closeDeleteModal() {
        setDeleteModal(false);
    }


    function openDataModal(item) {
        setSelectedItem(item);
        setDataModal(true);
    }

    function closeDataModal() {
        setDataModal(false);
    }

    function isEditMode() {
        return selectedItem && selectedItem.id;
    }

    return (
        <>
            <div className="text-center mb-4">
                <button type="button" className="btn btn-primary btn-lg px-4"  onClick={() => openDataModal()}>
                    Criar novo
                </button>
            </div>

            {loading && <div className="text-center">
                <div className="spinner-border" role="status"></div>
            </div>}

            {!loading && <>
                <table className="table">
                    <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Número</th>
                            <th scope="col">Estado</th>
                            <th scope="col">Operações</th>
                        </tr>
                    </thead>
                    <tbody>
                        { rooms.map((room, index) => <tr key={index} className="align-middle">
                            <th scope="row">{room.id}</th>
                            <td>{room.numero}</td>
                            <td>{room.estado}</td>
                            <td className="d-flex gap-2">
                                <button type="button" className="btn btn-outline-primary" onClick={() => openDataModal(room)}>Editar</button>
                                <button type="button" className="btn btn-outline-danger" onClick={() => showDeleteModal(room)}>Remover</button>
                            </td>
                        </tr>) }
                    </tbody>
                </table>

                { rooms.length === 0 && <div className="text-center">
                    Sem nenhum registro.
                </div> }
            </>}

            <Modal show={dataModal} onHide={closeDataModal}>
                <Modal.Header closeButton>
                    <Modal.Title>{isEditMode() ? 'Editar' : 'Criar'} Apartamento {isEditMode() && selectedItem.numero}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-3" controlId="numero">
                            <Form.Label>Número</Form.Label>
                            <Form.Control type="number" placeholder="Número"
                                value={selectedItem ? selectedItem.numero : 0}
                                onChange={(e) => setSelectedItem((prev = {}) => ({...prev, numero: e.target.value}))}
                            />
                        </Form.Group>

                        { isEditMode() && <Form.Group className="mb-3" controlId="estado">
                            <Form.Label>Estado</Form.Label>

                            <Form.Select
                                value={selectedItem ? selectedItem.estado : 'LIVRE'}
                                onChange={(e) => setSelectedItem((prev) => ({...prev, estado: e.target.value}))}
                            >
                                <option value="LIVRE">LIVRE</option>
                                <option value="LOCADO">LOCADO</option>
                            </Form.Select>
                        </Form.Group> }
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={closeDataModal}>
                        Cancelar
                    </Button>
                    <Button variant="primary" onClick={createOrUpdate}>
                        Gravar
                    </Button>
                </Modal.Footer>
            </Modal>

            <Modal show={deleteModal} onHide={closeDeleteModal}>
                <Modal.Header closeButton>
                    <Modal.Title>Apagar Apartamento</Modal.Title>
                </Modal.Header>
                <Modal.Body>Deseja mesmo apagar este apartamento de número {selectedItem && selectedItem.numero}?</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={closeDeleteModal}>
                        Cancelar
                    </Button>
                    <Button variant="primary" onClick={deleteItem}>
                        Ok
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}