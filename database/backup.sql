--
-- PostgreSQL database dump
--

-- Dumped from database version 14.2 (Debian 14.2-1.pgdg110+1)
-- Dumped by pg_dump version 14.2

-- Started on 2023-05-15 15:54:40

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 209 (class 1259 OID 16715)
-- Name: carrello; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.carrello (
    email character varying(40) NOT NULL,
    "idProdotto" integer NOT NULL,
    "nomeProdotto" character varying(100) DEFAULT NULL::character varying,
    quantita integer,
    prezzo integer,
    deleted character varying(1) DEFAULT 'N'::character varying
);


ALTER TABLE public.carrello OWNER TO root;

--
-- TOC entry 210 (class 1259 OID 16720)
-- Name: contatore; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.contatore (
    "idContatore" character varying(45) NOT NULL,
    contatore integer
);


ALTER TABLE public.contatore OWNER TO root;

--
-- TOC entry 211 (class 1259 OID 16723)
-- Name: prodotto; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.prodotto (
    "idProdotto" integer NOT NULL,
    "nomeProdotto" character varying(100) NOT NULL,
    "prezzoUnitario" integer NOT NULL,
    "urlImage" character varying(45),
    quantita integer,
    descrizione text,
    vetrina character varying(1) DEFAULT 'N'::character varying,
    blocked character varying(1) DEFAULT 'N'::character varying NOT NULL,
    annata character varying(4) DEFAULT '0000'::character varying NOT NULL,
    deleted character varying(1) DEFAULT 'N'::character varying NOT NULL
);


ALTER TABLE public.prodotto OWNER TO root;

--
-- TOC entry 212 (class 1259 OID 16732)
-- Name: prodotto_idProdotto_seq; Type: SEQUENCE; Schema: public; Owner: root
--

ALTER TABLE public.prodotto ALTER COLUMN "idProdotto" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."prodotto_idProdotto_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 213 (class 1259 OID 16733)
-- Name: riga_ordine; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.riga_ordine (
    "idRigaOrdine" integer NOT NULL,
    quantita integer,
    prezzo integer,
    annullato character varying(1) DEFAULT 'N'::character varying,
    arrivato character varying(1) DEFAULT 'N'::character varying,
    "idTestataOrdine" integer NOT NULL,
    "idProdotto" integer NOT NULL
);


ALTER TABLE public.riga_ordine OWNER TO root;

--
-- TOC entry 214 (class 1259 OID 16738)
-- Name: riga_ordine_idRigaOrdine_seq; Type: SEQUENCE; Schema: public; Owner: root
--

ALTER TABLE public.riga_ordine ALTER COLUMN "idRigaOrdine" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."riga_ordine_idRigaOrdine_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 215 (class 1259 OID 16739)
-- Name: testata_ordine; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.testata_ordine (
    "idTestataOrdine" integer NOT NULL,
    "emailUtente" character varying(45) NOT NULL,
    nome character varying(45) DEFAULT NULL::character varying,
    cognome character varying(45) DEFAULT NULL::character varying,
    indirizzo character varying(45) DEFAULT NULL::character varying,
    "codicePostale" character varying(5) DEFAULT NULL::character varying,
    citta character varying(45) DEFAULT NULL::character varying,
    provincia character varying(45) DEFAULT NULL::character varying,
    regione character varying(45) DEFAULT NULL::character varying,
    "dataOrdine" date,
    "statoSpedizione" character varying(45) DEFAULT 'e in fase di elaborazione'::character varying,
    annullato character varying(1) DEFAULT 'N'::character varying,
    consegnato character varying(1) DEFAULT 'N'::character varying
);


ALTER TABLE public.testata_ordine OWNER TO root;

--
-- TOC entry 216 (class 1259 OID 16752)
-- Name: utente; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.utente (
    email character varying(40) NOT NULL,
    nome character varying(45) NOT NULL,
    cognome character varying(45) NOT NULL,
    sesso character varying(1),
    password character varying(45) NOT NULL,
    "annoNascita" date NOT NULL,
    cellulare character varying(20) DEFAULT '000000'::character varying,
    "isAdmin" character varying(1) DEFAULT 'N'::character varying,
    deleted character varying(1) DEFAULT 'N'::character varying,
    "isBlocked" character varying(1) DEFAULT 'N'::character varying
);


ALTER TABLE public.utente OWNER TO root;

--
-- TOC entry 3368 (class 0 OID 16715)
-- Dependencies: 209
-- Data for Name: carrello; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.carrello (email, "idProdotto", "nomeProdotto", quantita, prezzo, deleted) FROM stdin;
ion.boleac@gmail.com	1	Champagne Brut Blanc de Blancs Grand Cru "Origine"	10	280	N
\.


--
-- TOC entry 3369 (class 0 OID 16720)
-- Dependencies: 210
-- Data for Name: contatore; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.contatore ("idContatore", contatore) FROM stdin;
idContatore	16
\.


--
-- TOC entry 3370 (class 0 OID 16723)
-- Dependencies: 211
-- Data for Name: prodotto; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.prodotto ("idProdotto", "nomeProdotto", "prezzoUnitario", "urlImage", quantita, descrizione, vetrina, blocked, annata, deleted) FROM stdin;
1	Champagne Brut Blanc de Blancs Grand Cru "Origine"	28	1637156649102.jpg	72	Si presenta di un bel giallo paglierino, con perlage fine e persistente. Allâolfattiva, i sentori fruttati vengono impreziositi da aromi secondari, che ricordano il lievito, la vaniglia e la crosta di pane. Fresco e fine il sorso, leggiadro e piacevole, rotondo e di buona lunghezza. Ã meraviglioso per accompagnare la cucina a base di pesce. Perfetto da abbinare al risotto con gamberi e burrata.	Y	N	2019	N
2	Champagne Brut "Carte Blanche" - Bernard Remy	19	1652707109486.jpg	0	Dorato; perlage fine e persistente. Al naso si esprime con note di agrumi, sentori di biancospino e tiglio e un cenno di miele. Al palato e' secco, fresco e di buona sapidita' , con un piacevole retrogusto di menta. Equilibrato.	Y	N	2021	N
3	Ion Boleac	100000000	1681739439841.jpg	0	Un ragazzo arrogante	N	N	1999	N
\.


--
-- TOC entry 3372 (class 0 OID 16733)
-- Dependencies: 213
-- Data for Name: riga_ordine; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.riga_ordine ("idRigaOrdine", quantita, prezzo, annullato, arrivato, "idTestataOrdine", "idProdotto") FROM stdin;
1	5	140	N	N	1	1
2	10	280	N	N	2	1
3	2	38	N	N	2	2
4	4	23	N	N	2	2
5	12	336	N	N	3	1
6	10	280	N	N	4	1
7	10	280	N	N	5	1
8	10	280	N	N	6	1
9	10	280	N	N	7	1
10	10	280	N	N	9	1
11	10	280	N	N	10	1
12	98	1862	N	N	11	2
13	1	28	N	N	11	1
14	10	1000000000	N	N	12	3
15	10	280	N	N	13	1
16	10	280	N	N	14	1
17	10	280	N	N	15	1
\.


--
-- TOC entry 3374 (class 0 OID 16739)
-- Dependencies: 215
-- Data for Name: testata_ordine; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.testata_ordine ("idTestataOrdine", "emailUtente", nome, cognome, indirizzo, "codicePostale", citta, provincia, regione, "dataOrdine", "statoSpedizione", annullato, consegnato) FROM stdin;
10	ion.boleac@gmail.com	Ion	Boleac	indirizzo test	12345	citta test	provincia test	regione test	2023-01-12	e in fase di elaborazione	N	N
11	v@gmail.com	ftrfcyg	ycfgvjhkfyy	rygjhkj	gjh	fchgvjh	dxfgchgvh	557537527537537	2023-04-17	e in fase di elaborazione	N	N
3	ion.boleac@gmail.com	\N	\N	indirizzo test	12345	citta test	provincia test	regione test	2022-11-17	e in fase di elaborazione	N	N
12	damianoManeskin@cocaina	Damiano	Maneskin	Via Marlena	torna	a casa	Catania	Abruzzo	2023-04-17	e in fase di elaborazione	N	N
13	ion.boleac@gmail.com	Ion	Boleac	indirizzo test	12345	citta test	provincia test	regione test	2023-05-14	e in fase di elaborazione	N	N
2	ion.boleac@gmail.com	Ion	Boleac	vi pippo, 11 	12345	Ferrara	Ferrara	Emilia Romagna	2022-06-10	e stato consegnato	N	Y
14	ion.boleac@gmail.com	Ion	Boleac	indirizzo test	12345	citta test	provincia test	regione test	2023-05-14	e in fase di elaborazione	N	N
15	ion.boleac@gmail.com	Ion	Boleac	indirizzo test	12345	citta test	provincia test	regione test	2023-05-14	e in fase di elaborazione	N	N
16	ion.boleac@gmail.com	Ion	Boleac	indirizzo test	12345	citta test	provincia test	regione test	2023-05-14	e in fase di elaborazione	N	N
5	ion.boleac@gmail.com	Ion	Boleac	indirizzo test	12345	citta test	provincia test	regione test	2022-11-17	e stato consegnato	Y	Y
1	ion.boleac@gmail.com	Ion	Boleac	Via don giovanottti	12345	Fe	Mondo	Narnia	2022-05-12	e in viaggio	N	N
4	ion.boleac@gmail.com	Ion	Boleac	indirizzo test	12345	citta test	provincia test	regione test	2022-11-17	e stato consegnato	Y	Y
6	ion.boleac@gmail.com	Ion	Boleac	indirizzo test	12345	citta test	provincia test	regione test	2022-11-24	e in fase di elaborazione	N	N
7	ion.boleac@gmail.com	Ion	Boleac	indirizzo test	12345	citta test	provincia test	regione test	2022-11-24	e in fase di elaborazione	N	N
8	ion.boleac@gmail.com	Ion	Boleac	indirizzo test	12345	citta test	provincia test	regione test	2023-01-04	e in fase di elaborazione	N	N
9	ion.boleac@gmail.com	Ion	Boleac	indirizzo test	12345	citta test	provincia test	regione test	2023-01-04	e in fase di elaborazione	N	N
\.


--
-- TOC entry 3375 (class 0 OID 16752)
-- Dependencies: 216
-- Data for Name: utente; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.utente (email, nome, cognome, sesso, password, "annoNascita", cellulare, "isAdmin", deleted, "isBlocked") FROM stdin;
16840228525901@gmail.com	Loggato	Loggato	S	password	1999-12-12	cellulare	Y	N	N
qwe@dssd	qwe	qwe	M	qwe	1999-11-11	156465	N	N	N
1668555842181@gmail.com	NonLoggato	NonLoggato	S	password	1999-12-12	cellulare	N	N	N
16685558421811@gmail.com	Loggato	Loggato	S	password	1999-12-12	cellulare	Y	N	N
1684024374319@gmail.com	NonLoggato	NonLoggato	S	password	1999-12-12	cellulare	N	N	N
email123@gmail.com	Nome	Cognome	S	password	1999-12-12	cellulare	Y	N	N
16840243743191@gmail.com	Loggato	Loggato	S	password	1999-12-12	cellulare	Y	N	N
ema5454il@gmail.com	Nome	Cognome	S	password	1999-12-12	cellulare	Y	N	N
1668556501017@gmail.com	NonLoggato	NonLoggato	S	password	1999-12-12	cellulare	N	N	N
ema255il@gmail.com	Nome	Cognome	S	password	1999-12-12	cellulare	Y	N	N
16685565010171@gmail.com	Loggato	Loggato	S	password	1999-12-12	cellulare	Y	N	N
1668615658369@gmail.com	NonLoggato	NonLoggato	S	password	1999-12-12	cellulare	N	N	N
65454email@gmail.com	Nome	Cognome	S	password	1999-12-12	cellulare	Y	N	N
16686156583691@gmail.com	Loggato	Loggato	S	password	1999-12-12	cellulare	Y	N	N
1684068809519@gmail.com	NonLoggato	NonLoggato	S	password	1999-12-12	cellulare	N	N	N
1668616000562@gmail.com	NonLoggato	NonLoggato	S	password	1999-12-12	cellulare	N	N	N
16686160005621@gmail.com	Loggato	Loggato	S	password	1999-12-12	cellulare	Y	N	N
em8478454ail@gmail.com	Nome	Cognome	S	password	1999-12-12	cellulare	Y	N	N
16840688095191@gmail.com	Loggato	Loggato	S	password	1999-12-12	cellulare	Y	N	N
em789456ail@gmail.com	Nome	Cognome	S	password	1999-12-12	cellulare	Y	N	N
1668700276220@gmail.com	NonLoggato	NonLoggato	S	password	1999-12-12	cellulare	N	N	N
16687002762201@gmail.com	Loggato	Loggato	S	password	1999-12-12	cellulare	Y	N	N
1668700394550@gmail.com	NonLoggato	NonLoggato	S	password	1999-12-12	cellulare	N	N	N
16687003945501@gmail.com	Loggato	Loggato	S	password	1999-12-12	cellulare	Y	N	N
silvia.boleac@gmail.com	Silvia	Boleac	F	qwe	1971-01-25	+393802456717	Y	Y	Y
1684071305656@gmail.com	NonLoggato	NonLoggato	S	password	1999-12-12	cellulare	N	N	N
em6ail@gmail.com	Nome	Cognome	S	password	1999-12-12	cellulare	Y	N	N
16840713056561@gmail.com	Loggato	Loggato	S	password	1999-12-12	cellulare	Y	N	N
1669252112722@gmail.com	NonLoggato	NonLoggato	S	password	1999-12-12	cellulare	N	N	N
16692521127221@gmail.com	Loggato	Loggato	S	password	1999-12-12	cellulare	Y	N	N
emadfdgfdgsil@gmail.com	Nome	Cognome	S	password	1999-12-12	cellulare	Y	N	N
1669252176747@gmail.com	NonLoggato	NonLoggato	S	password	1999-12-12	cellulare	N	N	N
16692521767471@gmail.com	Loggato	Loggato	S	password	1999-12-12	cellulare	Y	N	N
1672822441034@gmail.com	NonLoggato	NonLoggato	S	password	1999-12-12	cellulare	N	N	N
16728224410341@gmail.com	Loggato	Loggato	S	password	1999-12-12	cellulare	Y	N	N
1672822455473@gmail.com	NonLoggato	NonLoggato	S	password	1999-12-12	cellulare	N	N	N
16728224554731@gmail.com	Loggato	Loggato	S	password	1999-12-12	cellulare	Y	N	N
ema4545il@gmail.com	Nome	Cognome	S	password	1999-12-12	cellulare	Y	N	N
ion.boleac@gmail.com	Ion	Boleac	S	qwe	1999-12-12	cellulare	Y	N	N
1673530591008@gmail.com	NonLoggato	NonLoggato	S	password	1999-12-12	cellulare	N	N	N
1668520876149@gmail.com	NonLoggato	NonLoggato	S	password	1999-12-12	cellulare	N	N	N
16735305910081@gmail.com	Loggato	Loggato	S	password	1999-12-12	cellulare	Y	N	N
v@gmail.com	mic	vesp	F	asdfg	1999-05-12	345789632	N	N	N
1668523596404@gmail.com	NonLoggato	NonLoggato	S	password	1999-12-12	cellulare	N	N	N
16685235964041@gmail.com	Loggato	Loggato	S	password	1999-12-12	cellulare	Y	N	N
lorenzo.colombi@edu.unife.it	anj	asnkda	M	ciao1234	8155-12-27	5435165131	N	N	N
esistente@gmail.com	Esistente	Esistente	M	password	1999-12-12	cellulare	Y	N	N
e@mail	nome	cognome	\N	password	3050-01-01	sÃ¬	N	N	N
1668525925615@gmail.com	NonLoggato	NonLoggato	S	password	1999-12-12	cellulare	N	N	N
16685259256151@gmail.com	Loggato	Loggato	S	password	1999-12-12	cellulare	Y	N	N
ciao@ciao.it	ci	ao	F	ciaociao	2023-03-30	23	N	N	N
lorenzocolombi99@gmail.com	Lorenzo	Colombi	M	ciao1234	1999-07-27	aksdnkabdk	Y	N	N
damiano@damiano.it	asdas	asda	\N	damiano	1455-12-21	asda	Y	N	N
damianoManeskin@cocaina	Damiano	dei MAneskin	M	maneskin	1212-12-12	vicdeangelis	N	N	N
1684022852590@gmail.com	NonLoggato	NonLoggato	S	password	1999-12-12	cellulare	N	N	N
\.


--
-- TOC entry 3381 (class 0 OID 0)
-- Dependencies: 212
-- Name: prodotto_idProdotto_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public."prodotto_idProdotto_seq"', 3, true);


--
-- TOC entry 3382 (class 0 OID 0)
-- Dependencies: 214
-- Name: riga_ordine_idRigaOrdine_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public."riga_ordine_idRigaOrdine_seq"', 17, true);


--
-- TOC entry 3211 (class 2606 OID 16760)
-- Name: carrello carrello_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.carrello
    ADD CONSTRAINT carrello_pkey PRIMARY KEY (email, "idProdotto");


--
-- TOC entry 3213 (class 2606 OID 16762)
-- Name: contatore contatore_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.contatore
    ADD CONSTRAINT contatore_pkey PRIMARY KEY ("idContatore");


--
-- TOC entry 3221 (class 2606 OID 16764)
-- Name: utente email; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.utente
    ADD CONSTRAINT email UNIQUE (email);


--
-- TOC entry 3215 (class 2606 OID 16766)
-- Name: prodotto prodotto_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.prodotto
    ADD CONSTRAINT prodotto_pkey PRIMARY KEY ("idProdotto");


--
-- TOC entry 3217 (class 2606 OID 16768)
-- Name: riga_ordine riga_ordine_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.riga_ordine
    ADD CONSTRAINT riga_ordine_pkey PRIMARY KEY ("idRigaOrdine", "idTestataOrdine", "idProdotto");


--
-- TOC entry 3219 (class 2606 OID 16770)
-- Name: testata_ordine testata_ordine_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.testata_ordine
    ADD CONSTRAINT testata_ordine_pkey PRIMARY KEY ("idTestataOrdine");


--
-- TOC entry 3223 (class 2606 OID 16772)
-- Name: utente utente_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.utente
    ADD CONSTRAINT utente_pkey PRIMARY KEY (email);


--
-- TOC entry 3224 (class 2606 OID 16773)
-- Name: carrello email; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.carrello
    ADD CONSTRAINT email FOREIGN KEY (email) REFERENCES public.utente(email) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 3228 (class 2606 OID 16778)
-- Name: testata_ordine emailUtente; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.testata_ordine
    ADD CONSTRAINT "emailUtente" FOREIGN KEY ("emailUtente") REFERENCES public.utente(email) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3225 (class 2606 OID 16783)
-- Name: carrello idProdotto; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.carrello
    ADD CONSTRAINT "idProdotto" FOREIGN KEY ("idProdotto") REFERENCES public.prodotto("idProdotto") ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 3226 (class 2606 OID 16788)
-- Name: riga_ordine idProdotto; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.riga_ordine
    ADD CONSTRAINT "idProdotto" FOREIGN KEY ("idProdotto") REFERENCES public.prodotto("idProdotto") ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3227 (class 2606 OID 16793)
-- Name: riga_ordine idTestataOrdine; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.riga_ordine
    ADD CONSTRAINT "idTestataOrdine" FOREIGN KEY ("idTestataOrdine") REFERENCES public.testata_ordine("idTestataOrdine") ON UPDATE CASCADE ON DELETE CASCADE;


-- Completed on 2023-05-15 15:54:41

--
-- PostgreSQL database dump complete
--

